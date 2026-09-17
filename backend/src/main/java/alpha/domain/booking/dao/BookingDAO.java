package alpha.domain.booking.dao;

import alpha.domain.booking.entity.Booking;
import alpha.dao.EntityManagerDAO;
import jakarta.persistence.EntityManager;

public class BookingDAO extends EntityManagerDAO<Booking> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public BookingDAO(EntityManager em){
        super(em, Booking.class);
    }

}
