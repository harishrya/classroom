package com.example.classroomattendancemarkingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classroomattendancemarkingsystem.model.Subject;
import com.example.classroomattendancemarkingsystem.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectWebController {

    private final SubjectService subjectService;

    public SubjectWebController(
            SubjectService subjectService) {

        this.subjectService = subjectService;
    }

    // ==========================================
    // SHOW ALL SUBJECTS
    // URL: /subjects
    // ==========================================

    @GetMapping
    public String showSubjects(Model model) {

        model.addAttribute(
                "subjects",
                subjectService.getAllSubjects()
        );

        return "subjects";
    }

    // ==========================================
    // SHOW ADD FORM
    // URL: /subjects/new
    // ==========================================

    @GetMapping("/new")
    public String showAddForm(Model model) {

        model.addAttribute(
                "subject",
                new Subject()
        );

        return "subject-form";
    }

    // ==========================================
    // SAVE SUBJECT
    // URL: /subjects/save
    // ==========================================

    @PostMapping("/save")
    public String saveSubject(
            @ModelAttribute("subject") Subject subject) {

        subjectService.createSubject(subject);

        return "redirect:/subjects";
    }

    // ==========================================
    // SHOW EDIT FORM
    // URL: /subjects/edit/{id}
    // ==========================================

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Subject subject =
                subjectService.getSubjectById(id);

        model.addAttribute(
                "subject",
                subject
        );

        return "subject-edit";
    }

    // ==========================================
    // UPDATE SUBJECT
    // URL: /subjects/update/{id}
    // ==========================================

    @PostMapping("/update/{id}")
    public String updateSubject(
            @PathVariable Long id,
            @ModelAttribute("subject") Subject subject) {

        subjectService.updateSubject(
                id,
                subject
        );

        return "redirect:/subjects";
    }

    // ==========================================
    // DELETE SUBJECT
    // URL: /subjects/delete/{id}
    // ==========================================

    @GetMapping("/delete/{id}")
    public String deleteSubject(
            @PathVariable Long id) {

        subjectService.deleteSubject(id);

        return "redirect:/subjects";
    }
}