package alpha.domain.settings.operatinghour.controller;

import alpha.crud.CRUDController;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.settings.operatinghour.mapper.response.OperatingHourResponseMapper;
import alpha.service.EntityManagerService;

public class OperatingHourController extends CRUDController<OperatingHour> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public OperatingHourController(EntityManagerService<OperatingHour> service) {
        super(service, OperatingHour.class, OperatingHourResponseMapper::toDTO);
    }

}