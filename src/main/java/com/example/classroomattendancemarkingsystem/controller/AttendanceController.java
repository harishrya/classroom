package com.example.classroomattendancemarkingsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.classroomattendancemarkingsystem.model.AttendanceRecord;
import com.example.classroomattendancemarkingsystem.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
    }

    // =====================================================
    // GET ALL
    // GET /api/attendance
    // =====================================================

    @GetMapping
    public ResponseEntity<List<AttendanceRecord>> getAllAttendance() {

        return ResponseEntity.ok(
                attendanceService.getAllAttendance()
        );
    }

    // =====================================================
    // GET ONE
    // GET /api/attendance/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceRecord> getAttendance(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id)
        );
    }

    // =====================================================
    // MARK ATTENDANCE
    // POST /api/attendance/mark
    // =====================================================

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(
            @RequestParam Long studentId,
            @RequestParam Long sessionId,
            @RequestParam boolean present) {

        try {

            AttendanceRecord record =
                    attendanceService.markAttendance(
                            studentId,
                            sessionId,
                            present
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(record);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    // =====================================================
    // UPDATE
    // PUT /api/attendance/{id}
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendance(
            @PathVariable Long id,
            @RequestParam boolean present) {

        try {

            AttendanceRecord record =
                    attendanceService.updateAttendance(
                            id,
                            present
                    );

            return ResponseEntity.ok(record);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    // =====================================================
    // DELETE
    // DELETE /api/attendance/{id}
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendance(
            @PathVariable Long id) {

        try {

            attendanceService.deleteAttendance(id);

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Attendance deleted successfully"
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    // =====================================================
    // STUDENT ATTENDANCE
    // GET /api/attendance/student/{studentId}
    // =====================================================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceRecord>>
    getStudentAttendance(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                attendanceService
                        .getStudentAttendance(studentId)
        );
    }

    // =====================================================
    // STUDENT PERCENTAGE
    // GET /api/attendance/student/{studentId}/percentage
    // =====================================================

    @GetMapping("/student/{studentId}/percentage")
    public ResponseEntity<Map<String, Object>>
    getStudentPercentage(
            @PathVariable Long studentId) {

        double percentage =
                attendanceService
                        .getAttendancePercentage(studentId);

        boolean shortage =
                attendanceService
                        .hasShortage(studentId);

        return ResponseEntity.ok(
                Map.of(
                        "studentId", studentId,
                        "percentage", percentage,
                        "minimumRequired",
                        attendanceService
                                .getMinimumAttendance(),
                        "shortage", shortage
                )
        );
    }

    // =====================================================
    // SESSION ATTENDANCE
    // GET /api/attendance/session/{sessionId}
    // =====================================================

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<AttendanceRecord>>
    getSessionAttendance(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                attendanceService
                        .getSessionAttendance(sessionId)
        );
    }
}