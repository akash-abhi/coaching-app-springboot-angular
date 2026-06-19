package com.coaching.controller;

import com.coaching.dto.ApiResponse;
import com.coaching.model.Video;
import com.coaching.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadVideo(@RequestBody Video video) {
        try {
            Video uploadedVideo = videoService.uploadVideo(video);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Video uploaded successfully")
                            .data(uploadedVideo)
                            .statusCode(201)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVideoById(@PathVariable Long id) {
        try {
            Video video = videoService.getVideoById(id)
                    .orElseThrow(() -> new RuntimeException("Video not found"));
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Video retrieved successfully")
                            .data(video)
                            .statusCode(200)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getVideosByCourse(@PathVariable Long courseId) {
        try {
            List<Video> videos = videoService.getVideosByCourse(courseId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Videos retrieved successfully")
                            .data(videos)
                            .statusCode(200)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateVideo(@PathVariable Long id, @RequestBody Video videoDetails) {
        try {
            Video updatedVideo = videoService.updateVideo(id, videoDetails);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Video updated successfully")
                            .data(updatedVideo)
                            .statusCode(200)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteVideo(@PathVariable Long id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Video deleted successfully")
                            .statusCode(200)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }
}
