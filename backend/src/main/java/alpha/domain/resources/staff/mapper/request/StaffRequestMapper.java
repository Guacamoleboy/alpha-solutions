package alpha.domain.resources.staff.mapper.request;

import alpha.domain.resources.staff.dto.request.StaffRequestDTO;
import alpha.domain.resources.staff.entity.Staff;

public class StaffRequestMapper {

    // _________________________________________________________________________________________________________________

    // Maps StaffRequestDTO to Staff entity
    // ____________________________________
    //
    //      StaffRequestDTO
    //              ↓
    //      StaffRequestMapper
    //              ↓
    //      Staff
    //
    // ____________________
    // Tested: NO
    // Last Tested: N/A

    // _________________________________________________________________________________________________________________

    public static Staff toEntity(StaffRequestDTO dto) {
        Staff staff = new Staff();
        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setEmail(dto.getEmail());
        staff.setPhone(dto.getPhone());
        staff.setSalary(dto.getSalary());
        staff.setWorkingHoursWeekly(dto.getWorkingHoursWeekly());
        return staff;
    }

}