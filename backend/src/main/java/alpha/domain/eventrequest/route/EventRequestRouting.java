package alpha.domain.eventrequest.route;

import alpha.crud.CRUDRouting;
import alpha.domain.eventrequest.controller.EventRequestController;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.eventrequest.service.EventRequestService;
import alpha.domain.eventorganizer.service.EventOrganizerService;
import alpha.domain.member.service.MemberService;
import alpha.domain.settings.operatinghour.service.OperatingHourService;
import alpha.domain.court.service.CourtService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import static io.javalin.apibuilder.ApiBuilder.get;

public class EventRequestRouting extends CRUDRouting<EventRequest> {

    // Attributes

    // _________________________________________________________________________________________________________________

    public EventRequestRouting(EntityManagerFactory emf) {
        super("/event-requests", createController(emf));
    }

    // _________________________________________________________________________________________________________________

    @Override
    protected void customRoutes() {
        EventRequestController eventRequestController = (EventRequestController) controller;
        get("/mine", eventRequestController::getMine);
    }

    // _________________________________________________________________________________________________________________

    private static EventRequestController createController(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        MemberService memberService = new MemberService(em);
        EventOrganizerService eventOrganizerService = new EventOrganizerService(em);
        OperatingHourService operatingHourService = new OperatingHourService(em);
        CourtService courtService = new CourtService(em);
        return new EventRequestController(new EventRequestService(em, memberService, eventOrganizerService, operatingHourService, courtService));
    }

}