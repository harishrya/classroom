package com.example.classroomattendancemarkingsystem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.model.Subject;
import com.example.classroomattendancemarkingsystem.repository.SessionRepository;
import com.example.classroomattendancemarkingsystem.repository.SubjectRepository;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SubjectRepository subjectRepository;

    public SessionService(
            SessionRepository sessionRepository,
            SubjectRepository subjectRepository) {

        this.sessionRepository = sessionRepository;
        this.subjectRepository = subjectRepository;
    }

    // ==========================================
    // GET ALL SESSIONS
    // ==========================================

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    // ==========================================
    // GET SESSION BY ID
    // ==========================================

    public Session getSessionById(Long id) {

        return sessionRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Session not found with ID: " + id
                    )
                );
    }

    // ==========================================
    // GET SESSIONS BY SUBJECT
    // ==========================================

    public List<Session> getSessionsBySubject(Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Subject not found with ID: " + subjectId
                    )
                );

        return sessionRepository.findBySubject(subject);
    }

    // ==========================================
    // CREATE SESSION
    // ==========================================

    public Session createSession(
            Long subjectId,
            Session session) {

        if (session.getSessionDate() == null) {
            throw new RuntimeException(
                "Session date is required"
            );
        }

        if (session.getStartTime() == null) {
            throw new RuntimeException(
                "Start time is required"
            );
        }

        if (session.getEndTime() == null) {
            throw new RuntimeException(
                "End time is required"
            );
        }

        if (!session.getEndTime()
                .isAfter(session.getStartTime())) {

            throw new RuntimeException(
                "End time must be after start time"
            );
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Subject not found with ID: " + subjectId
                    )
                );

        session.setSubject(subject);

        return sessionRepository.save(session);
    }

    // ==========================================
    // UPDATE SESSION
    // ==========================================

    public Session updateSession(
            Long id,
            Long subjectId,
            Session updatedSession) {

        Session existingSession =
                getSessionById(id);

        if (updatedSession.getSessionDate() == null) {
            throw new RuntimeException(
                "Session date is required"
            );
        }

        if (updatedSession.getStartTime() == null) {
            throw new RuntimeException(
                "Start time is required"
            );
        }

        if (updatedSession.getEndTime() == null) {
            throw new RuntimeException(
                "End time is required"
            );
        }

        if (!updatedSession.getEndTime()
                .isAfter(updatedSession.getStartTime())) {

            throw new RuntimeException(
                "End time must be after start time"
            );
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Subject not found with ID: " + subjectId
                    )
                );

        existingSession.setSubject(subject);
        existingSession.setSessionDate(
            updatedSession.getSessionDate()
        );
        existingSession.setStartTime(
            updatedSession.getStartTime()
        );
        existingSession.setEndTime(
            updatedSession.getEndTime()
        );

        return sessionRepository.save(existingSession);
    }

    // ==========================================
    // DELETE SESSION
    // ==========================================

    public void deleteSession(Long id) {

        if (!sessionRepository.existsById(id)) {
            throw new RuntimeException(
                "Session not found with ID: " + id
            );
        }

        sessionRepository.deleteById(id);
    }

    // ==========================================
    // GET SESSIONS BY DATE
    // ==========================================

    public List<Session> getSessionsByDate(
            LocalDate date) {

        return sessionRepository.findBySessionDate(date);
    }
}