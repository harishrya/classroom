package com.example.classroomattendancemarkingsystem.controller;

import com.example.classroomattendancemarkingsystem.model.AttendanceRecord;
import com.example.classroomattendancemarkingsystem.service.AttendanceService;
import com.example.classroomattendancemarkingsystem.service.SessionService;
import com.example.classroomattendancemarkingsystem.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceWebController {

    private final AttendanceService attendanceService;
    private final StudentService studentService;
    private final SessionService sessionService;

    public AttendanceWebController(
            AttendanceService attendanceService,
            StudentService studentService,
            SessionService sessionService) {

        this.attendanceService = attendanceService;
        this.studentService = studentService;
        this.sessionService = sessionService;
    }

    // =====================================================
    // SHOW ALL ATTENDANCE
    // URL: /attendance
    // =====================================================

    @GetMapping
    public String attendance(Model model) {

        model.addAttribute(
                "attendanceList",
                attendanceService.getAllAttendance()
        );

        return "attendance";
    }

    // =====================================================
    // SHOW MARK ATTENDANCE PAGE
    // URL: /attendance/new
    // =====================================================

    @GetMapping("/new")
    public String newAttendance(Model model) {

        model.addAttribute(
                "students",
                studentService.getAllStudents()
        );

        model.addAttribute(
                "sessions",
                sessionService.getAllSessions()
        );

        return "attendance-form";
    }

    // =====================================================
    // SAVE ATTENDANCE
    // POST /attendance/save
    // =====================================================

    @PostMapping("/save")
    public String saveAttendance(
            @RequestParam Long studentId,
            @RequestParam Long sessionId,
            @RequestParam boolean present,
            Model model) {

        try {

            attendanceService.markAttendance(
                    studentId,
                    sessionId,
                    present
            );

            return "redirect:/attendance";

        } catch (RuntimeException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            model.addAttribute(
                    "students",
                    studentService.getAllStudents()
            );

            model.addAttribute(
                    "sessions",
                    sessionService.getAllSessions()
            );

            return "attendance-form";
        }
    }

    // =====================================================
    // EDIT ATTENDANCE
    // GET /attendance/edit/{id}
    // =====================================================

    @GetMapping("/edit/{id}")
    public String editAttendance(
            @PathVariable Long id,
            Model model) {

        AttendanceRecord attendance =
                attendanceService.getAttendanceById(id);

        model.addAttribute(
                "attendance",
                attendance
        );

        return "attendance-edit";
    }

    // =====================================================
    // UPDATE ATTENDANCE
    // POST /attendance/update/{id}
    // =====================================================

    @PostMapping("/update/{id}")
    public String updateAttendance(
            @PathVariable Long id,
            @RequestParam boolean present) {

        attendanceService.updateAttendance(
                id,
                present
        );

        return "redirect:/attendance";
    }

    // =====================================================
    // DELETE ATTENDANCE
    // GET /attendance/delete/{id}
    // =====================================================

    @GetMapping("/delete/{id}")
    public String deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return "redirect:/attendance";
    }

    // =====================================================
    // STUDENT ATTENDANCE
    // GET /attendance/student/{studentId}
    // =====================================================

    @GetMapping("/student/{studentId}")
    public String studentAttendance(
            @PathVariable Long studentId,
            Model model) {

        model.addAttribute(
                "attendanceList",
                attendanceService
                        .getStudentAttendance(studentId)
        );

        model.addAttribute(
                "percentage",
                attendanceService
                        .getAttendancePercentage(studentId)
        );

        model.addAttribute(
                "shortage",
                attendanceService
                        .hasShortage(studentId)
        );

        model.addAttribute(
                "minimumAttendance",
                attendanceService
                        .getMinimumAttendance()
        );

        return "student-attendance";
    }
}