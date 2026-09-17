package alpha.domain.booking.route;

import alpha.domain.booking.controller.BookingController;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.service.BookingService;
import alpha.crud.CRUDRouting;
import alpha.domain.court.service.CourtService;
import alpha.domain.member.service.MemberService;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import jakarta.persistence.EntityManager;
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
        EntityManager em = emf.createEntityManager();
        CourtService courtService = new CourtService(em);
        MemberService memberService = new MemberService(em);
        OperatingHourService operatingHourService = new OperatingHourService(em);
        BookingService bookingService = new BookingService(em, courtService, memberService, operatingHourService);
        return new BookingController(bookingService);
    }

}