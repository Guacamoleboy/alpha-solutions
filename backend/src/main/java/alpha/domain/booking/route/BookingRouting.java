package alpha.domain.booking.route;

import alpha.domain.booking.controller.BookingController;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.service.BookingService;
import alpha.crud.CRUDRouting;
import jakarta.persistence.EntityManagerFactory;

public class BookingRouting extends CRUDRouting<Booking> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public BookingRouting(EntityManagerFactory emf) {
        super("/booking", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    @Override
    protected void customRoutes() {
        BookingController bookingController = (BookingController) controller;
        // Custom endpoints here
        // Example: post("/path", controller::method);
    }

    // _________________________________________________________________________________________________________________

    private static BookingController createController(EntityManagerFactory emf) {
        return new BookingController(new BookingService(emf.createEntityManager()));
    }

}