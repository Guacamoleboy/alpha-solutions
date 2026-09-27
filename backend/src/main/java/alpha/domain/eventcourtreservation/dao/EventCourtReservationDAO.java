package alpha.domain.eventcourtreservation.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventrequest.enums.EventRequestStatus;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class EventCourtReservationDAO extends EntityManagerDAO<EventCourtReservation> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventCourtReservationDAO(EntityManager em) {
        super(em, EventCourtReservation.class);
    }

    // _________________________________________________________________________________________________________________

    public boolean existsAcceptedReservationOverlap(Integer courtId, LocalDateTime startTime, LocalDateTime endTime) {
        return executeQuery(() -> {
            Long count = em.createQuery("""
                    SELECT COUNT(reservation)
                    FROM EventCourtReservation reservation
                    WHERE reservation.court.id = :courtId
                    AND reservation.eventRequest.status = :status
                    AND reservation.eventRequest.startTime < :endTime
                    AND reservation.eventRequest.endTime > :startTime
                    """, Long.class)
                    .setParameter("courtId", courtId)
                    .setParameter("status", EventRequestStatus.ACCEPTED)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();
            return count > 0;
        });
    }

    // _________________________________________________________________________________________________________________

    public boolean existsOtherAcceptedEventOverlap(Integer courtId, Integer eventRequestId, LocalDateTime startTime, LocalDateTime endTime) {
        return executeQuery(() -> {
            Long count = em.createQuery("""
                    SELECT COUNT(reservation)
                    FROM EventCourtReservation reservation
                    WHERE reservation.court.id = :courtId
                    AND reservation.eventRequest.id <> :eventRequestId
                    AND reservation.eventRequest.status = :status
                    AND reservation.eventRequest.startTime < :endTime
                    AND reservation.eventRequest.endTime > :startTime
                    """, Long.class)
                    .setParameter("courtId", courtId)
                    .setParameter("eventRequestId", eventRequestId)
                    .setParameter("status", EventRequestStatus.ACCEPTED)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();
            return count > 0;
        });
    }
    
}