package ru.esphere.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.esphere.model.AsyncTask;
import ru.esphere.service.TaskExecutor;
import ru.esphere.service.TrackService;

import java.util.Objects;
import java.util.Optional;

import static ru.esphere.utils.Utils.currentUser;

@Service
public class TrackServiceImpl implements TrackService {
    private final TaskExecutor taskExecutor;

    @Autowired
    public TrackServiceImpl(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public Optional<AsyncTask> getTrackInfo(String trackId) {
        return Optional.ofNullable(trackId)
                .flatMap(taskExecutor::getAndRemoveIfDone)
                .filter(at -> Objects.equals(at.getOwner(), currentUser().orElse(null)));
    }
}
