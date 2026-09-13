package alpha.config;

import alpha.entity.*;
import org.hibernate.cfg.Configuration;

public class HibernateAnnotation {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Member.class);
        configuration.addAnnotatedClass(Membership.class);
    }

}