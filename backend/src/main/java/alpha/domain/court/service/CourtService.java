package alpha.domain.court.service;

import alpha.domain.court.dao.CourtDAO;
import alpha.domain.court.dto.request.CourtRequestDTO;
import alpha.domain.court.entity.Court;
import alpha.domain.court.mapper.request.CourtRequestMapper;
import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.service.MembershipService;
import alpha.exception.ApiException;
import alpha.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public class CourtService extends EntityManagerService<Court> {

    // Attributes
    private final CourtDAO courtDAO;
    private final MembershipService membershipService;

    // _________________________________________________________________________________________________________________

    public CourtService(EntityManager em) {
        super(new CourtDAO(em), Court.class);
        this.courtDAO = (CourtDAO) this.entityManagerDAO;
        this.membershipService = new MembershipService(em);
    }

    // _________________________________________________________________________________________________________________

    public Court createCourt(CourtRequestDTO dto) {
        Court court = CourtRequestMapper.toEntity(dto);
        court.setRequiredMembership(getMembership(dto.getRequiredMembershipId()));
        return create(court);
    }

    // _________________________________________________________________________________________________________________

    public Court updateCourt(Integer id, CourtRequestDTO dto) {
        Court court = courtDAO.getById(id);
        if (court == null) {
            throw new ApiException(404, "Court not found");
        }

        if (dto.getName() != null) court.setName(dto.getName());
        if (dto.getActive() != null) court.setActive(dto.getActive());
        if (dto.getSurface() != null) court.setSurface(dto.getSurface());
        if (dto.getLatitude() != null) court.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) court.setLongitude(dto.getLongitude());
        if (dto.getOrientationDegrees() != null) court.setOrientationDegrees(dto.getOrientationDegrees());
        if (dto.getElevation() != null) court.setElevation(dto.getElevation());
        court.setRequiredMembership(getMembership(dto.getRequiredMembershipId()));

        return update(court);
    }

    // _________________________________________________________________________________________________________________

    private Membership getMembership(Integer membershipId) {
        if (membershipId == null) {
            return null;
        }
        Membership membership = membershipService.getById(membershipId);
        if (membership == null) {
            throw new ApiException(404, "Membership not found");
        }
        return membership;
    }

}