package com.example.classroomattendancemarkingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classroomattendancemarkingsystem.model.Student;
import com.example.classroomattendancemarkingsystem.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    private final StudentService studentService;

    public StudentWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ==============================
    // SHOW ALL STUDENTS
    // /students
    // ==============================

    @GetMapping
    public String students(Model model) {

        model.addAttribute(
            "students",
            studentService.getAllStudents()
        );

        return "students";
    }

    // ==============================
    // SHOW ADD FORM
    // /students/new
    // ==============================

    @GetMapping("/new")
    public String newStudent(Model model) {

        model.addAttribute(
            "student",
            new Student()
        );

        return "student-form";
    }

    // ==============================
    // SAVE STUDENT
    // /students/save
    // ==============================

    @PostMapping("/save")
    public String saveStudent(
            @ModelAttribute("student") Student student) {

        studentService.createStudent(student);

        return "redirect:/students";
    }

    // ==============================
    // EDIT FORM
    // /students/edit/{id}
    // ==============================

    @GetMapping("/edit/{id}")
    public String editStudent(
            @PathVariable Long id,
            Model model) {

        Student student =
                studentService.getStudentById(id);

        model.addAttribute(
            "student",
            student
        );

        return "student-edit";
    }

    // ==============================
    // UPDATE STUDENT
    // /students/update/{id}
    // ==============================

    @PostMapping("/update/{id}")
    public String updateStudent(
            @PathVariable Long id,
            @ModelAttribute("student") Student student) {

        studentService.updateStudent(id, student);

        return "redirect:/students";
    }

    // ==============================
    // DELETE STUDENT
    // /students/delete/{id}
    // ==============================

    @GetMapping("/delete/{id}")
    public String deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return "redirect:/students";
    }
}