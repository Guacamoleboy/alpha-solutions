package alpha.domain.booking.dao;

import alpha.domain.booking.entity.Booking;
import alpha.dao.EntityManagerDAO;
import alpha.domain.booking.enums.BookingStatus;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class BookingDAO extends EntityManagerDAO<Booking> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public BookingDAO(EntityManager em){
        super(em, Booking.class);
    }

    // _________________________________________________________________________________________________________________

    public boolean existsOverlappingBooking(Integer courtId, LocalDateTime startTime, LocalDateTime endTime) {
        return executeQuery(() -> {
            String JPQL = """
                SELECT COUNT(b)
                FROM Booking b
                WHERE b.court.id = :courtId
                AND b.status <> :cancelled
                AND b.startTime < :endTime
                AND b.endTime > :startTime
            """;

            Long count = em.createQuery(JPQL, Long.class)
                    .setParameter("courtId", courtId)
                    .setParameter("cancelled", BookingStatus.CANCELLED)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();
            return count > 0;

        });
    }

    // _________________________________________________________________________________________________________________

    public boolean existsBookingForMemberOnDate(Integer memberId, LocalDateTime startTime) {
        return executeQuery(() -> {
            String JPQL = """
            SELECT COUNT(b)
            FROM Booking b
            WHERE b.member.id = :memberId
            AND b.status <> :cancelled
            AND b.startTime >= :startOfDay
            AND b.startTime < :startOfNextDay
        """;

            Long count = em.createQuery(JPQL, Long.class)
                    .setParameter("memberId", memberId)
                    .setParameter("cancelled", BookingStatus.CANCELLED)
                    .setParameter("startOfDay", startTime.toLocalDate().atStartOfDay())
                    .setParameter("startOfNextDay", startTime.toLocalDate().plusDays(1).atStartOfDay())
                    .getSingleResult();

            return count > 0;
        });
    }

}