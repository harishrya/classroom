package com.example.classroomattendancemarkingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classroomattendancemarkingsystem.model.Student;
import com.example.classroomattendancemarkingsystem.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found with ID: " + id)
                );
    }

    // Create student
    public Student createStudent(Student student) {

        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            throw new RuntimeException(
                "Roll number already exists: " + student.getRollNumber()
            );
        }

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException(
                "Email already exists: " + student.getEmail()
            );
        }

        return studentRepository.save(student);
    }

    // Update student
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existingStudent = getStudentById(id);

        // Check roll number belongs to another student
        studentRepository.findByRollNumber(updatedStudent.getRollNumber())
                .ifPresent(student -> {
                    if (!student.getStudentId().equals(id)) {
                        throw new RuntimeException(
                            "Roll number already exists: "
                            + updatedStudent.getRollNumber()
                        );
                    }
                });

        // Check email belongs to another student
        studentRepository.findByEmail(updatedStudent.getEmail())
                .ifPresent(student -> {
                    if (!student.getStudentId().equals(id)) {
                        throw new RuntimeException(
                            "Email already exists: "
                            + updatedStudent.getEmail()
                        );
                    }
                });

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setRollNumber(updatedStudent.getRollNumber());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setContact(updatedStudent.getContact());

        return studentRepository.save(existingStudent);
    }

    // Delete student
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException(
                "Student not found with ID: " + id
            );
        }

        studentRepository.deleteById(id);
    }
}