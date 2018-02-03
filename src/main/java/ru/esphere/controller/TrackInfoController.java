package ru.esphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.esphere.service.TrackService;

@RestController
@RequestMapping("/api/track")
public class TrackInfoController {
    private final TrackService trackService;

    @Autowired
    public TrackInfoController(TrackService trackService) {
        this.trackService = trackService;
    }

    @RequestMapping("/{trackId}")
    public ResponseEntity<?> getTrackInfo(@PathVariable("trackId") String trackId) {
        return trackService.getTrackInfo(trackId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity("Task not found", HttpStatus.NOT_FOUND));
    }
}
