package alpha.domain.booking.mapper.request;

import alpha.domain.booking.dto.request.BookingRequestDTO;
import alpha.domain.booking.entity.Booking;

public class BookingRequestMapper {

    // _________________________________________________________________________________________________________________

    // Maps BookingRequestDTO to Booking entity
    // ________________________________________
    //
    //       BookingRequestDTO
    //              ↓
    //       BookingRequestMapper
    //              ↓
    //       Booking
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static Booking toEntity(BookingRequestDTO dto) {

        Booking booking = new Booking();

        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());

        return booking;

    }

}