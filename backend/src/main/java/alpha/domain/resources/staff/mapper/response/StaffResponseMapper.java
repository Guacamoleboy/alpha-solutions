package alpha.domain.resources.staff.mapper.response;

import alpha.domain.resources.staff.dto.response.StaffResponseDTO;
import alpha.domain.resources.staff.entity.Staff;

public class StaffResponseMapper {

    // _________________________________________________________________________________________________________________

    // Maps Staff entity to StaffResponseDTO
    // _____________________________________
    //
    //      Staff
    //               ↓
    //      StaffResponseMapper
    //               ↓
    //      StaffResponseDTO
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static StaffResponseDTO toDTO(Staff staff) {
        StaffResponseDTO dto = new StaffResponseDTO();
        dto.setId(staff.getId());
        dto.setFirstName(staff.getFirstName());
        dto.setLastName(staff.getLastName());
        dto.setEmail(staff.getEmail());
        dto.setPhone(staff.getPhone());
        dto.setSalary(staff.getSalary());
        dto.setWorkingHoursWeekly(staff.getWorkingHoursWeekly());

        if (staff.getRole() != null) {
            dto.setRole(staff.getRole().getName());
        }

        dto.setCreatedAt(staff.getCreatedAt());
        return dto;

    }

}