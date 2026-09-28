package com.example.classroomattendancemarkingsystem.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.service.SessionService;
import com.example.classroomattendancemarkingsystem.service.SubjectService;

@Controller
@RequestMapping("/sessions")
public class SessionWebController {

    private final SessionService sessionService;
    private final SubjectService subjectService;

    public SessionWebController(
            SessionService sessionService,
            SubjectService subjectService) {

        this.sessionService = sessionService;
        this.subjectService = subjectService;
    }


    // =====================================================
    // SHOW ALL SESSIONS
    // =====================================================

    @GetMapping
    public String showSessions(Model model) {

        model.addAttribute(
                "sessions",
                sessionService.getAllSessions()
        );

        return "sessions";
    }


    // =====================================================
    // SHOW ADD SESSION FORM
    // =====================================================

    @GetMapping("/new")
    public String showAddForm(Model model) {

        // Do NOT use session as Thymeleaf variable
        // inside HTML with th:object="${session}"

        model.addAttribute(
                "subjects",
                subjectService.getAllSubjects()
        );

        return "session-form";
    }


    // =====================================================
    // SAVE SESSION
    // =====================================================

    @PostMapping("/save")
    public String saveSession(

            @RequestParam("subjectId")
            Long subjectId,

            @RequestParam("sessionDate")
            LocalDate sessionDate,

            @RequestParam("startTime")
            LocalTime startTime,

            @RequestParam("endTime")
            LocalTime endTime) {


        Session session = new Session();


        session.setSessionDate(
                sessionDate
        );


        session.setStartTime(
                startTime
        );


        session.setEndTime(
                endTime
        );


        sessionService.createSession(
                subjectId,
                session
        );


        return "redirect:/sessions";
    }


    // =====================================================
    // DELETE SESSION
    // =====================================================

    @GetMapping("/delete/{id}")
    public String deleteSession(
            @PathVariable Long id) {

        sessionService.deleteSession(id);

        return "redirect:/sessions";
    }

}