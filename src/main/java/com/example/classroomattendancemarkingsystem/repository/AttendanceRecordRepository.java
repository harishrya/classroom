package com.example.classroomattendancemarkingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classroomattendancemarkingsystem.model.AttendanceRecord;
import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.model.Student;

@Repository
public interface AttendanceRecordRepository
        extends JpaRepository<AttendanceRecord, Long> {

    boolean existsByStudentAndSession(
            Student student,
            Session session
    );

    List<AttendanceRecord> findByStudent(
            Student student
    );

    List<AttendanceRecord> findBySession(
            Session session
    );

    long countByStudent(
            Student student
    );

    long countByStudentAndPresent(
            Student student,
            boolean present
    );
}