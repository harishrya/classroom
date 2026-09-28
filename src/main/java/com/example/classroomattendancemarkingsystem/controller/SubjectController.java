package com.example.classroomattendancemarkingsystem.controller;

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

import com.example.classroomattendancemarkingsystem.model.Subject;
import com.example.classroomattendancemarkingsystem.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // ==========================================
    // GET ALL SUBJECTS
    // GET /api/subjects
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {

        return ResponseEntity.ok(
                subjectService.getAllSubjects()
        );
    }

    // ==========================================
    // GET SUBJECT BY ID
    // GET /api/subjects/{id}
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                subjectService.getSubjectById(id)
        );
    }

    // ==========================================
    // CREATE SUBJECT
    // POST /api/subjects
    // ==========================================

    @PostMapping
    public ResponseEntity<Subject> createSubject(
            @RequestBody Subject subject) {

        Subject savedSubject =
                subjectService.createSubject(subject);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedSubject);
    }

    // ==========================================
    // UPDATE SUBJECT
    // PUT /api/subjects/{id}
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable Long id,
            @RequestBody Subject subject) {

        Subject updatedSubject =
                subjectService.updateSubject(id, subject);

        return ResponseEntity.ok(updatedSubject);
    }

    // ==========================================
    // DELETE SUBJECT
    // DELETE /api/subjects/{id}
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(
            @PathVariable Long id) {

        subjectService.deleteSubject(id);

        return ResponseEntity.ok(
                "Subject deleted successfully"
        );
    }
}