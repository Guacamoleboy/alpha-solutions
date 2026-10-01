package alpha.domain.courtclosure.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.courtclosure.entity.CourtClosure;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class CourtClosureDAO extends EntityManagerDAO<CourtClosure> {

    // _________________________________________________________________________________________________________________

    public CourtClosureDAO(EntityManager em) {
        super(em, CourtClosure.class);
    }

    // _________________________________________________________________________________________________________________

    public boolean existsOverlappingClosure(Integer courtId, LocalDateTime startTime, LocalDateTime endTime) {
        return executeQuery(() -> {
            Long count = em.createQuery("""
                    SELECT COUNT(closure)
                    FROM CourtClosure closure
                    WHERE closure.court.id = :courtId
                    AND closure.startTime < :endTime
                    AND closure.endTime > :startTime
                    """, Long.class)
                    .setParameter("courtId", courtId)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();
            return count > 0;
        });
    }

}