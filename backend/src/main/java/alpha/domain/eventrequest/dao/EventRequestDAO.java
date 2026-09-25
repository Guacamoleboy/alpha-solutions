package alpha.domain.eventrequest.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.eventrequest.entity.EventRequest;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class EventRequestDAO extends EntityManagerDAO<EventRequest> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventRequestDAO(EntityManager em) {
        super(em, EventRequest.class);
    }

    // _________________________________________________________________________________________________________________

    public boolean existsForMemberOnDate(Integer memberId, LocalDate date) {
        return executeQuery(() -> {
            Long count = em.createQuery(
                            "SELECT COUNT(eventRequest) FROM EventRequest eventRequest " +
                                    "LEFT JOIN eventRequest.organizers organizer " +
                                    "WHERE (eventRequest.requester.id = :memberId OR organizer.member.id = :memberId) " +
                                    "AND eventRequest.startTime >= :dayStart " +
                                    "AND eventRequest.startTime < :nextDay",
                            Long.class
                    )
                    .setParameter("memberId", memberId)
                    .setParameter("dayStart", date.atStartOfDay())
                    .setParameter("nextDay", date.plusDays(1).atStartOfDay())
                    .getSingleResult();
            return count != null && count > 0;
        });
    }
    
    // _________________________________________________________________________________________________________________

    public List<EventRequest> findAllForMember(Integer memberId) {
        return executeQuery(() -> em.createQuery(
                        "SELECT DISTINCT eventRequest FROM EventRequest eventRequest " +
                                "LEFT JOIN eventRequest.organizers organizer " +
                                "WHERE eventRequest.requester.id = :memberId " +
                                "OR organizer.member.id = :memberId " +
                                "ORDER BY eventRequest.startTime",
                        EventRequest.class
                )
                .setParameter("memberId", memberId)
                .getResultList());
    }

}