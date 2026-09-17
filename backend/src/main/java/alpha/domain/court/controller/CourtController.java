package alpha.domain.court.controller;

import alpha.domain.court.entity.Court;
import alpha.domain.court.mapper.response.CourtResponseMapper;
import alpha.crud.CRUDController;
import alpha.service.EntityManagerService;

public class CourtController extends CRUDController<Court> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public CourtController(EntityManagerService<Court> service) {
        super(service, Court.class, CourtResponseMapper::toDTO);
    }

}