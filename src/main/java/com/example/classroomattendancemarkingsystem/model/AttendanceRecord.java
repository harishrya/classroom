package com.example.classroomattendancemarkingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "attendance_records",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_student_session",
            columnNames = {"student_id", "session_id"}
        )
    }
)
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "student_id",
        nullable = false
    )
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
        name = "session_id",
        nullable = false
    )
    private Session session;

    @Column(
        name = "present",
        nullable = false
    )
    private boolean present;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public AttendanceRecord() {
    }

    public AttendanceRecord(
            Student student,
            Session session,
            boolean present) {

        this.student = student;
        this.session = session;
        this.present = present;
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public Long getAttendanceId() {
        return attendanceId;
    }

    public Student getStudent() {
        return student;
    }

    public Session getSession() {
        return session;
    }

    public boolean isPresent() {
        return present;
    }

    // =====================================================
    // SETTERS
    // =====================================================

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}