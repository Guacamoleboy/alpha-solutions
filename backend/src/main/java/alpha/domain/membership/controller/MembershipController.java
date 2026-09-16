package alpha.domain.membership.controller;

import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.mapper.response.MembershipResponseMapper;
import alpha.service.EntityManagerService;
import alpha.crud.CRUDController;

public class MembershipController extends CRUDController<Membership> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MembershipController(EntityManagerService<Membership> service) {
        super(service, Membership.class, MembershipResponseMapper::toDTO);
    }

}

