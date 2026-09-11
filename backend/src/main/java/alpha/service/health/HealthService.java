package alpha.service.health;

import jakarta.persistence.EntityManager;

public class HealthService {

    // Attributes
    private final EntityManager em;

    // _________________________________________________________________________________________________________________

    public HealthService(EntityManager em) {
        this.em = em;
    }

    // _________________________________________________________________________________________________________________

    public String checkHealth() {
        try {
            em.createQuery("SELECT 1").getSingleResult();
            return "Health OK";
        } catch (Exception e) {
            return "Health FAILED: " + e.getMessage();
        }
    }

}