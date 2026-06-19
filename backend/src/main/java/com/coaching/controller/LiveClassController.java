package com.coaching.controller;

import com.coaching.dto.ApiResponse;
import com.coaching.model.LiveClass;
import com.coaching.service.LiveClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/live-classes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LiveClassController {
    @Autowired
    private LiveClassService liveClassService;

    @PostMapping
    public ResponseEntity<ApiResponse> createLiveClass(@RequestBody LiveClass liveClass) {
        try {
            LiveClass createdClass = liveClassService.createLiveClass(liveClass);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Live class created successfully")
                            .data(createdClass)
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
    public ResponseEntity<ApiResponse> getLiveClassById(@PathVariable Long id) {
        try {
            LiveClass liveClass = liveClassService.getLiveClassById(id)
                    .orElseThrow(() -> new RuntimeException("Live class not found"));
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Live class retrieved successfully")
                            .data(liveClass)
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
    public ResponseEntity<ApiResponse> getLiveClassesByCourse(@PathVariable Long courseId) {
        try {
            List<LiveClass> classes = liveClassService.getLiveClassesByCourse(courseId);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Live classes retrieved successfully")
                            .data(classes)
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

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse> getUpcomingClasses() {
        try {
            List<LiveClass> upcomingClasses = liveClassService.getUpcomingLiveClasses();
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Upcoming classes retrieved successfully")
                            .data(upcomingClasses)
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

    @PutMapping("/{id}/start")
    public ResponseEntity<ApiResponse> startLiveClass(@PathVariable Long id) {
        try {
            LiveClass liveClass = liveClassService.startLiveClass(id);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Live class started successfully")
                            .data(liveClass)
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

    @PutMapping("/{id}/end")
    public ResponseEntity<ApiResponse> endLiveClass(@PathVariable Long id) {
        try {
            LiveClass liveClass = liveClassService.endLiveClass(id);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Live class ended successfully")
                            .data(liveClass)
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
