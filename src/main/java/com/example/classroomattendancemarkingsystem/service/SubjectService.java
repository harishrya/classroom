package com.example.classroomattendancemarkingsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classroomattendancemarkingsystem.model.Subject;
import com.example.classroomattendancemarkingsystem.repository.SubjectRepository;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // ==========================================
    // GET ALL SUBJECTS
    // ==========================================

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // ==========================================
    // GET SUBJECT BY ID
    // ==========================================

    public Subject getSubjectById(Long id) {

        return subjectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Subject not found with ID: " + id
                        )
                );
    }

    // ==========================================
    // CREATE SUBJECT
    // ==========================================

    public Subject createSubject(Subject subject) {

        if (subject.getSubjectCode() == null ||
                subject.getSubjectCode().trim().isEmpty()) {

            throw new RuntimeException(
                    "Subject code is required"
            );
        }

        if (subject.getSubjectName() == null ||
                subject.getSubjectName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Subject name is required"
            );
        }

        String code = subject.getSubjectCode()
                .trim()
                .toUpperCase();

        subject.setSubjectCode(code);

        if (subjectRepository.existsBySubjectCode(code)) {

            throw new RuntimeException(
                    "Subject code already exists: " + code
            );
        }

        return subjectRepository.save(subject);
    }

    // ==========================================
    // UPDATE SUBJECT
    // ==========================================

    public Subject updateSubject(
            Long id,
            Subject updatedSubject) {

        Subject existingSubject = getSubjectById(id);

        if (updatedSubject.getSubjectCode() == null ||
                updatedSubject.getSubjectCode().trim().isEmpty()) {

            throw new RuntimeException(
                    "Subject code is required"
            );
        }

        if (updatedSubject.getSubjectName() == null ||
                updatedSubject.getSubjectName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Subject name is required"
            );
        }

        String code = updatedSubject.getSubjectCode()
                .trim()
                .toUpperCase();

        OptionalCheck(existingSubject, code);

        existingSubject.setSubjectCode(code);

        existingSubject.setSubjectName(
                updatedSubject.getSubjectName().trim()
        );

        return subjectRepository.save(existingSubject);
    }

    // ==========================================
    // CHECK DUPLICATE CODE DURING UPDATE
    // ==========================================

    private void OptionalCheck(
            Subject existingSubject,
            String code) {

        subjectRepository.findBySubjectCode(code)
                .ifPresent(subject -> {

                    if (!subject.getSubjectId()
                            .equals(existingSubject.getSubjectId())) {

                        throw new RuntimeException(
                                "Subject code already exists: " + code
                        );
                    }
                });
    }

    // ==========================================
    // DELETE SUBJECT
    // ==========================================

    public void deleteSubject(Long id) {

        if (!subjectRepository.existsById(id)) {

            throw new RuntimeException(
                    "Subject not found with ID: " + id
            );
        }

        subjectRepository.deleteById(id);
    }
}