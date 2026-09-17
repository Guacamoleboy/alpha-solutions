package alpha.domain.booking.mapper.response;

import alpha.domain.booking.dto.response.BookingResponseDTO;
import alpha.domain.booking.entity.Booking;
import alpha.domain.member.entity.Member;

public class BookingResponseMapper {

    // _________________________________________________________________________________________________________________

    // Maps Booking entity to BookingResponseDTO
    // _________________________________________
    //
    //       Booking
    //               ↓
    //       BookingResponseMapper
    //               ↓
    //       BookingResponseDTO
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static BookingResponseDTO toDTO(Booking booking) {

        BookingResponseDTO dto = new BookingResponseDTO();

        dto.setId(booking.getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setStatus(booking.getStatus());
        dto.setCreatedAt(booking.getCreatedAt());

        // Member isn't invalid
        if (booking.getMember() != null) {
            Member member = booking.getMember();
            dto.setMemberId(member.getId());
            dto.setMemberName(member.getFirstName() + " " + member.getLastName());
        }

        // If court exists
        if (booking.getCourt() != null) {
            dto.setCourtId(booking.getCourt().getId());
            dto.setCourtName(booking.getCourt().getName());
        }

        return dto;

    }

}