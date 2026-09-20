package alpha.domain.resources.staff.service;

import alpha.domain.resources.staff.dao.StaffDAO;
import alpha.domain.resources.staff.dto.request.StaffRequestDTO;
import alpha.domain.resources.staff.entity.Staff;
import alpha.domain.role.entity.Role;
import alpha.domain.role.enums.RoleName;
import alpha.domain.role.service.RoleService;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import alpha.util.BCryptHash;
import jakarta.persistence.EntityManager;

public class StaffService extends EntityManagerService<Staff> {

    // Attributes
    public static final String DEFAULT_PASSWORD = "Password12345!";
    private final RoleService roleService;

    // _________________________________________________________________________________________________________________

    public StaffService(EntityManager em) {
        super(new StaffDAO(em), Staff.class);
        this.roleService = new RoleService(em);
    }

    // _________________________________________________________________________________________________________________

    public Staff createStaff(Staff staff) {
        staff.setPasswordHashed(BCryptHash.hash(DEFAULT_PASSWORD));
        Role role = roleService.getByName(RoleName.STAFF);
        if (role == null) {
            throw new ApiException(500, "Default staff role not found");
        }
        staff.setRole(role);
        return create(staff);
    }

    public Staff updateStaff(Integer id, StaffRequestDTO dto) {
        Staff staff = ((StaffDAO) entityManagerDAO).getById(id);

        if (staff == null) {
            throw new ApiException(404, "Staff not found");
        }

        if (dto.getFirstName() != null) {
            staff.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            staff.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            staff.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            staff.setPhone(dto.getPhone());
        }
        if (dto.getSalary() != null) {
            staff.setSalary(dto.getSalary());
        }
        if (dto.getWorkingHoursWeekly() != null) {
            staff.setWorkingHoursWeekly(dto.getWorkingHoursWeekly());
        }

        return update(staff);
    }

}
