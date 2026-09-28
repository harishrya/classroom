package com.example.classroomattendancemarkingsystem.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.classroomattendancemarkingsystem.model.Session;
import com.example.classroomattendancemarkingsystem.model.Subject;
import com.example.classroomattendancemarkingsystem.repository.SessionRepository;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SubjectService subjectService;

    public SessionService(
            SessionRepository sessionRepository,
            SubjectService subjectService) {

        this.sessionRepository = sessionRepository;
        this.subjectService = subjectService;
    }


    // =====================================================
    // GET ALL SESSIONS
    // =====================================================

    public List<Session> getAllSessions() {

        return sessionRepository.findAll();
    }


    // =====================================================
    // GET SESSION BY ID
    // =====================================================

    public Session getSessionById(Long id) {

        return sessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session not found with ID: " + id
                        )
                );
    }


    // =====================================================
    // CREATE SESSION
    // =====================================================

    public Session createSession(
            Long subjectId,
            Session session) {

        // Get subject from database
        Subject subject =
                subjectService.getSubjectById(subjectId);

        // Set subject
        session.setSubject(subject);

        // Validate session
        validateSession(session);

        // Save
        return sessionRepository.save(session);
    }


    // =====================================================
    // UPDATE SESSION
    // =====================================================

    public Session updateSession(
            Long id,
            Long subjectId,
            Session session) {

        // Get existing session
        Session existingSession =
                getSessionById(id);

        // Get subject
        Subject subject =
                subjectService.getSubjectById(subjectId);

        // Set updated values
        existingSession.setSubject(subject);

        existingSession.setSessionDate(
                session.getSessionDate()
        );

        existingSession.setStartTime(
                session.getStartTime()
        );

        existingSession.setEndTime(
                session.getEndTime()
        );

        // Validate
        validateSession(existingSession);

        // Save
        return sessionRepository.save(
                existingSession
        );
    }


    // =====================================================
    // DELETE SESSION
    // =====================================================

    public void deleteSession(Long id) {

        if (!sessionRepository.existsById(id)) {

            throw new RuntimeException(
                    "Session not found with ID: " + id
            );
        }

        sessionRepository.deleteById(id);
    }


    // =====================================================
    // VALIDATE SESSION
    // =====================================================

    private void validateSession(Session session) {

        if (session.getSubject() == null) {

            throw new RuntimeException(
                    "Subject is required"
            );
        }


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


        LocalTime startTime =
                session.getStartTime();

        LocalTime endTime =
                session.getEndTime();


        if (!endTime.isAfter(startTime)) {

            throw new RuntimeException(
                    "End time must be after start time"
            );
        }
    }

}