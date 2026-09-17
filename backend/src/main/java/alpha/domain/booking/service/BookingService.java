package alpha.domain.booking.service;

import alpha.domain.booking.dao.BookingDAO;
import alpha.domain.booking.entity.Booking;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class BookingService extends EntityManagerService<Booking> {

    // Attributes
    private final BookingDAO bookingDAO;

    // _________________________________________________________________________________________________________________

    public BookingService(EntityManager em){
        super(new BookingDAO(em), Booking.class);
        this.bookingDAO = (BookingDAO) this.entityManagerDAO;
    }

}
