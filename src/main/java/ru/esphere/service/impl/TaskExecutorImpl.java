package ru.esphere.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.esphere.model.AsyncTask;
import ru.esphere.model.TaskStatus;
import ru.esphere.service.TaskExecutor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static ru.esphere.utils.Utils.currentUser;

@Service
@EnableScheduling
public class TaskExecutorImpl implements TaskExecutor {
    private final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
    private final ConcurrentMap<String, AsyncTask> taskMap;
    private final ExecutorService executorService;

    @Autowired
    public TaskExecutorImpl(ExecutorService executorService) {
        this.executorService = executorService;
        taskMap = new ConcurrentHashMap<>();
    }

    @Override
    public AsyncTaskFuture submit(Callable action) {
        AsyncTask asyncTask = makeTask();
        Future<?> future = executorService.submit(() -> {
            taskMap.computeIfPresent(asyncTask.getTrackId(), (k, v) -> v.toInProgress());
            try {
                final Object result = action.call();
                taskMap.computeIfPresent(asyncTask.getTrackId(), (k, v) -> v.toSuccess(result));
            } catch (Throwable t) {
                logger.warn("Task {} completed with an error: {}. Task owner: {}", asyncTask.getTrackId(), t, asyncTask.getOwner());
                taskMap.computeIfPresent(asyncTask.getTrackId(), (k, v) -> v.toError(t));
            }
        });
        return new AsyncTaskFuture(future, this, asyncTask.getTrackId());
    }

    @Override
    public Optional<AsyncTask> getAndRemoveIfDone(String trackId) {
        AtomicReference<AsyncTask> res = new AtomicReference<>();
        taskMap.computeIfPresent(trackId, (k, v) -> {
            res.set(v);
            return v.isDone() ? null : v;
        });

        return Optional.ofNullable(res.get());
    }

    public AsyncTask get(String trackId) {
        return taskMap.get(trackId);
    }

    private AsyncTask makeTask() {
        String user = currentUser().orElse(null);

        String id;
        AsyncTask asyncTask;
        do {
            id = UUID.randomUUID().toString();
            asyncTask = new AsyncTask(id, user, TaskStatus.PENDING, null, null);
        } while (taskMap.putIfAbsent(id, asyncTask) != null);

        return asyncTask;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void expireOldTasks() {
        long timeToRemove = System.currentTimeMillis() - (1000 * 60 * 30);
        for (Map.Entry<String, AsyncTask> e : taskMap.entrySet()) {
            if (e.getValue().getUpdateTimestamp() < timeToRemove && e.getValue().isDone()) {
                logger.info("Expire old task: {}", e.getKey());
                taskMap.remove(e.getKey());
            }
        }
    }

    public class AsyncTaskFuture {
        private final Future future;
        private final TaskExecutorImpl taskExecutor;
        private final String id;

        private AsyncTaskFuture(Future<?> future, TaskExecutorImpl taskExecutor, String id) {
            this.future = future;
            this.taskExecutor = taskExecutor;
            this.id = id;
        }

        public AsyncTask get() {
            return taskExecutor.get(id);
        }

        public AsyncTask get(long timeout, TimeUnit unit) {
            try {
                future.get(timeout, unit);
                return taskExecutor.getAndRemoveIfDone(id)
                        .orElse(null);//impossible
            } catch (Throwable ignored) {
                return get();
            }
        }
    }
}
