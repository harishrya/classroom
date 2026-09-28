package com.example.classroomattendancemarkingsystem.repository;

import com.example.classroomattendancemarkingsystem.model.AttendanceRecord;
import com.example.classroomattendancemarkingsystem.model.Student;
import com.example.classroomattendancemarkingsystem.model.Session;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository
        extends JpaRepository<AttendanceRecord, Long> {

    Optional<AttendanceRecord> findByStudentAndSession(
            Student student,
            Session session
    );

    List<AttendanceRecord> findByStudent(Student student);

    List<AttendanceRecord> findBySession(Session session);

    long countByStudent(Student student);

    long countByStudentAndPresent(
            Student student,
            boolean present
    );

    boolean existsByStudentAndSession(
            Student student,
            Session session
    );
}