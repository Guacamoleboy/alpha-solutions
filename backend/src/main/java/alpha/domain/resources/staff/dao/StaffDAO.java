package alpha.domain.resources.staff.dao;

import alpha.dao.EntityManagerDAO;
import alpha.domain.resources.staff.entity.Staff;
import jakarta.persistence.EntityManager;

public class StaffDAO extends EntityManagerDAO<Staff> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public StaffDAO(EntityManager em) {
        super(em, Staff.class);
    }

}