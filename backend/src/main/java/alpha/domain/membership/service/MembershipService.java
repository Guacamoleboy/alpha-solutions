package alpha.domain.membership.service;

import alpha.domain.membership.dao.MembershipDAO;
import alpha.domain.membership.dto.request.MembershipRequestDTO;
import alpha.domain.membership.entity.Membership;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class MembershipService extends EntityManagerService<Membership> {

    // Attributes
    private final MembershipDAO membershipDAO;

    // _________________________________________________________________________________________________________________

    public MembershipService(EntityManager em){
        super(new MembershipDAO(em), Membership.class);
        this.membershipDAO = (MembershipDAO) this.entityManagerDAO;
    }

    public Membership updateMembership(Integer id, MembershipRequestDTO dto) {
        Membership membership = getById(id);
        if (membership == null) {
            throw new ApiException(404, "Membership not found");
        }

        if (dto.getName() != null) membership.setName(dto.getName());
        if (dto.getDescription() != null) membership.setDescription(dto.getDescription());
        if (dto.getPrice() != null) membership.setPrice(dto.getPrice());
        if (dto.getCurrency() != null) membership.setCurrency(dto.getCurrency());
        if (dto.getDuration() != null) membership.setDuration(dto.getDuration());
        if (dto.getStartDate() != null) membership.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) membership.setEndDate(dto.getEndDate());
        if (dto.getGuestPass() != null) membership.setGuestPass(dto.getGuestPass());
        if (dto.getActive() != null) membership.setActive(dto.getActive());

        return update(membership);
    }

}
