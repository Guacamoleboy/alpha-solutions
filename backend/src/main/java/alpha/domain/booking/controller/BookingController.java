package alpha.domain.booking.controller;

import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.mapper.response.BookingResponseMapper;
import alpha.crud.CRUDController;
import alpha.service.EntityManagerService;

public class BookingController extends CRUDController<Booking> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public BookingController(EntityManagerService<Booking> service) {
        super(service, Booking.class, BookingResponseMapper::toDTO);
    }

}