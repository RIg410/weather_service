package ru.esphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class AsyncTask {
    private final String trackId;
    private final TaskStatus status;
    private final Object result;
    private final String err;
    @JsonIgnore
    private final long updateTimestamp;
    @JsonIgnore
    private final String owner;

    public AsyncTask(String trackId, String owner, TaskStatus status, Object result, String err) {
        this.trackId = trackId;
        this.owner = owner;
        this.status = status;
        this.result = result;
        this.err = err;
        this.updateTimestamp = System.currentTimeMillis();
    }

    public String getTrackId() {
        return trackId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @SuppressWarnings("unchecked")
    public <T> T getResult() {
        return (T) result;
    }

    public String getErr() {
        return err;
    }


    public long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsyncTask asyncTask = (AsyncTask) o;
        return updateTimestamp == asyncTask.updateTimestamp &&
                Objects.equals(trackId, asyncTask.trackId) &&
                status == asyncTask.status &&
                Objects.equals(result, asyncTask.result) &&
                Objects.equals(err, asyncTask.err) &&
                Objects.equals(owner, asyncTask.owner);
    }

    @Override
    public String toString() {
        return "AsyncTask{" +
                "trackId='" + trackId + '\'' +
                ", status=" + status +
                ", result=" + result +
                ", err='" + err + '\'' +
                ", updateTimestamp=" + updateTimestamp +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, status, result, err);
    }

    public static AsyncTask error(String error) {
        return new AsyncTask(null, null, TaskStatus.ERROR, null, error);
    }

    public AsyncTask toInProgress() {
        return new AsyncTask(trackId, owner, TaskStatus.IN_PROGRESS, result, err);
    }

    public AsyncTask toError(Throwable t) {
        return new AsyncTask(trackId, owner, TaskStatus.ERROR, null, t.getMessage());
    }

    public AsyncTask toSuccess(Object result) {
        return new AsyncTask(trackId, owner, TaskStatus.FINISH, result, null);
    }

    public boolean isDone() {
        return status == TaskStatus.FINISH || status == TaskStatus.ERROR;
    }
}
