package ru.esphere.service;

import ru.esphere.model.AsyncTask;
import ru.esphere.service.impl.TaskExecutorImpl;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface TaskExecutor {
    TaskExecutorImpl.AsyncTaskFuture submit(Callable action);

    Optional<AsyncTask> getAndRemoveIfDone(String trackId);
}
