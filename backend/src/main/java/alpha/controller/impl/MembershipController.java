package alpha.controller.impl;

import alpha.entity.Membership;
import alpha.mapper.response.MembershipResponseMapper;
import alpha.service.internal.EntityManagerService;

public class MembershipController extends CRUDController<Membership> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public MembershipController(EntityManagerService<Membership> service) {
        super(service, Membership.class, MembershipResponseMapper::toDTO);
    }

}