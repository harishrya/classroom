package com.example.classroomattendancemarkingsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.service.SessionService;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(
            SessionService sessionService) {

        this.sessionService = sessionService;
    }

    // ==========================================
    // GET ALL
    // GET /api/sessions
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {

        return ResponseEntity.ok(
            sessionService.getAllSessions()
        );
    }

    // ==========================================
    // GET BY ID
    // GET /api/sessions/1
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            sessionService.getSessionById(id)
        );
    }

    // ==========================================
    // GET BY SUBJECT
    // GET /api/sessions/subject/1
    // ==========================================

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<Session>> getBySubject(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
            sessionService.getSessionsBySubject(
                subjectId
            )
        );
    }

    // ==========================================
    // GET BY DATE
    // GET /api/sessions/date/2026-09-28
    // ==========================================

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Session>> getByDate(
            @PathVariable LocalDate date) {

        return ResponseEntity.ok(
            sessionService.getSessionsByDate(date)
        );
    }

    // ==========================================
    // CREATE
    // POST /api/sessions
    // ==========================================

    @PostMapping("/subject/{subjectId}")
    public ResponseEntity<Session> createSession(
            @PathVariable Long subjectId,
            @RequestBody Session session) {

        Session savedSession =
                sessionService.createSession(
                    subjectId,
                    session
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedSession);
    }

    // ==========================================
    // UPDATE
    // PUT /api/sessions/1/subject/1
    // ==========================================

    @PutMapping("/{id}/subject/{subjectId}")
    public ResponseEntity<Session> updateSession(
            @PathVariable Long id,
            @PathVariable Long subjectId,
            @RequestBody Session session) {

        Session updatedSession =
                sessionService.updateSession(
                    id,
                    subjectId,
                    session
                );

        return ResponseEntity.ok(updatedSession);
    }

    // ==========================================
    // DELETE
    // DELETE /api/sessions/1
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(
            @PathVariable Long id) {

        sessionService.deleteSession(id);

        return ResponseEntity.ok(
            "Session deleted successfully"
        );
    }
}