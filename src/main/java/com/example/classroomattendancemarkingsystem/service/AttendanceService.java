package com.example.classroomattendancemarkingsystem.service;

import com.example.classroomattendancemarkingsystem.model.AttendanceRecord;
import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.model.Student;
import com.example.classroomattendancemarkingsystem.repository.AttendanceRecordRepository;
import com.example.classroomattendancemarkingsystem.repository.SessionRepository;
import com.example.classroomattendancemarkingsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;

    // Change this value if your college requires another threshold.
    private static final double MINIMUM_ATTENDANCE = 75.0;

    public AttendanceService(
            AttendanceRecordRepository attendanceRepository,
            StudentRepository studentRepository,
            SessionRepository sessionRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.sessionRepository = sessionRepository;
    }

    // ---------------------------------------------------------
    // GET ALL ATTENDANCE
    // ---------------------------------------------------------

    public List<AttendanceRecord> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // ---------------------------------------------------------
    // GET BY ID
    // ---------------------------------------------------------

    public AttendanceRecord getAttendanceById(Long id) {

        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Attendance record not found: " + id
                        ));
    }

    // ---------------------------------------------------------
    // MARK ATTENDANCE
    // ---------------------------------------------------------

    @Transactional
    public AttendanceRecord markAttendance(
            Long studentId,
            Long sessionId,
            boolean present) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found: " + studentId
                        ));

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session not found: " + sessionId
                        ));

        /*
         * Business rule:
         * One student can be marked only once
         * for one session.
         */
        if (attendanceRepository
                .existsByStudentAndSession(student, session)) {

            throw new RuntimeException(
                    "Attendance already marked for this student and session."
            );
        }

        AttendanceRecord attendance = new AttendanceRecord();

        attendance.setStudent(student);
        attendance.setSession(session);
        attendance.setPresent(present);

        return attendanceRepository.save(attendance);
    }

    // ---------------------------------------------------------
    // UPDATE ATTENDANCE
    // ---------------------------------------------------------

    @Transactional
    public AttendanceRecord updateAttendance(
            Long id,
            boolean present) {

        AttendanceRecord attendance =
                getAttendanceById(id);

        attendance.setPresent(present);

        return attendanceRepository.save(attendance);
    }

    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------

    @Transactional
    public void deleteAttendance(Long id) {

        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException(
                    "Attendance record not found: " + id
            );
        }

        attendanceRepository.deleteById(id);
    }

    // ---------------------------------------------------------
    // STUDENT ATTENDANCE
    // ---------------------------------------------------------

    public List<AttendanceRecord> getStudentAttendance(
            Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found: " + studentId
                        ));

        return attendanceRepository.findByStudent(student);
    }

    // ---------------------------------------------------------
    // SESSION ATTENDANCE
    // ---------------------------------------------------------

    public List<AttendanceRecord> getSessionAttendance(
            Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session not found: " + sessionId
                        ));

        return attendanceRepository.findBySession(session);
    }

    // ---------------------------------------------------------
    // ATTENDANCE PERCENTAGE
    // ---------------------------------------------------------

    public double getAttendancePercentage(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found: " + studentId
                        ));

        long total =
                attendanceRepository.countByStudent(student);

        if (total == 0) {
            return 0.0;
        }

        long present =
                attendanceRepository
                        .countByStudentAndPresent(
                                student,
                                true
                        );

        return (present * 100.0) / total;
    }

    // ---------------------------------------------------------
    // SHORTAGE CHECK
    // ---------------------------------------------------------

    public boolean hasShortage(Long studentId) {

        double percentage =
                getAttendancePercentage(studentId);

        return percentage < MINIMUM_ATTENDANCE;
    }

    // ---------------------------------------------------------
    // GET THRESHOLD
    // ---------------------------------------------------------

    public double getMinimumAttendance() {
        return MINIMUM_ATTENDANCE;
    }
}