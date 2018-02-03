package ru.esphere.service;

import ru.esphere.model.AsyncTask;

import java.util.Optional;

public interface TrackService {
    Optional<AsyncTask> getTrackInfo(String trackId);
}
