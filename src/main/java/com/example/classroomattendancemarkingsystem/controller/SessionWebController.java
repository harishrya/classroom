package com.example.classroomattendancemarkingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // ==========================================
    // SHOW ALL SESSIONS
    // /sessions
    // ==========================================

    @GetMapping
    public String sessions(Model model) {

        model.addAttribute(
            "sessions",
            sessionService.getAllSessions()
        );

        return "sessions";
    }

    // ==========================================
    // SHOW ADD FORM
    // /sessions/new
    // ==========================================

    @GetMapping("/new")
    public String newSession(Model model) {

        model.addAttribute(
            "session",
            new Session()
        );

        model.addAttribute(
            "subjects",
            subjectService.getAllSubjects()
        );

        return "session-form";
    }

    // ==========================================
    // SAVE SESSION
    // /sessions/save
    // ==========================================

    @PostMapping("/save")
    public String saveSession(
            @RequestParam("subjectId") Long subjectId,
            @ModelAttribute("session") Session session) {

        sessionService.createSession(
            subjectId,
            session
        );

        return "redirect:/sessions";
    }

    // ==========================================
    // SHOW EDIT FORM
    // /sessions/edit/{id}
    // ==========================================

    @GetMapping("/edit/{id}")
    public String editSession(
            @PathVariable Long id,
            Model model) {

        Session session =
                sessionService.getSessionById(id);

        model.addAttribute(
            "session",
            session
        );

        model.addAttribute(
            "subjects",
            subjectService.getAllSubjects()
        );

        return "session-edit";
    }

    // ==========================================
    // UPDATE SESSION
    // ==========================================

    @PostMapping("/update/{id}")
    public String updateSession(
            @PathVariable Long id,
            @RequestParam("subjectId") Long subjectId,
            @ModelAttribute("session") Session session) {

        sessionService.updateSession(
            id,
            subjectId,
            session
        );

        return "redirect:/sessions";
    }

    // ==========================================
    // DELETE SESSION
    // ==========================================

    @GetMapping("/delete/{id}")
    public String deleteSession(
            @PathVariable Long id) {

        sessionService.deleteSession(id);

        return "redirect:/sessions";
    }
}