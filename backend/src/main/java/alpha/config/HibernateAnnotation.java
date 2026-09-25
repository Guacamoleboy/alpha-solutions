package alpha.config;

import alpha.domain.booking.entity.Booking;
import alpha.domain.court.entity.Court;
import alpha.domain.eventcourtreservation.entity.EventCourtReservation;
import alpha.domain.eventorganizer.entity.EventOrganizer;
import alpha.domain.eventrequest.entity.EventRequest;
import alpha.domain.member.entity.Member;
import alpha.domain.membership.entity.Membership;
import alpha.domain.role.entity.Role;
import alpha.domain.settings.operatinghour.entity.OperatingHour;
import alpha.domain.resources.staff.entity.Staff;
import org.hibernate.cfg.Configuration;

public class HibernateAnnotation {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Member.class);
        configuration.addAnnotatedClass(Membership.class);
        configuration.addAnnotatedClass(Booking.class);
        configuration.addAnnotatedClass(Court.class);
        configuration.addAnnotatedClass(OperatingHour.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Staff.class);
        configuration.addAnnotatedClass(EventRequest.class);
        configuration.addAnnotatedClass(EventOrganizer.class);
        configuration.addAnnotatedClass(EventCourtReservation.class);
    }

}