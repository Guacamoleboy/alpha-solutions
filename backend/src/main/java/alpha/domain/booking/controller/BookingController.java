package alpha.domain.booking.controller;

import alpha.domain.booking.dto.request.BookingRequestDTO;
import alpha.domain.booking.entity.Booking;
import alpha.domain.booking.mapper.response.BookingResponseMapper;
import alpha.crud.CRUDController;
import alpha.domain.booking.service.BookingService;
import alpha.security.jwt.JwtService;
import alpha.service.EntityManagerService;
import alpha.util.ContextHelper;
import alpha.util.TryCatchHelper;
import io.javalin.http.Context;

public class BookingController extends CRUDController<Booking> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public BookingController(EntityManagerService<Booking> service) {
        super(service, Booking.class, BookingResponseMapper::toDTO);
    }

    // _________________________________________________________________________________________________________________

    @Override
    public void create(Context ctx) {
        TryCatchHelper.tryCatchHelper(ctx, () -> {
            String token = ContextHelper.extractBearerToken(ctx);
            Integer memberId = JwtService.getClaimMemberId(token);
            BookingRequestDTO dto = ctx.bodyAsClass(BookingRequestDTO.class);
            Booking booking = ((BookingService) classService).createBooking(memberId, dto);
            return BookingResponseMapper.toDTO(booking);
        }, "Booking created");
    }

}