package com.example.classroomattendancemarkingsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.model.Subject;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findBySubject(Subject subject);

    List<Session> findBySessionDate(LocalDate sessionDate);

    List<Session> findBySubjectAndSessionDate(
            Subject subject,
            LocalDate sessionDate);
}