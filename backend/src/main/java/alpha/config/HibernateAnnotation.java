package alpha.config;

import alpha.domain.member.entity.Member;
import alpha.domain.membership.entity.Membership;
import org.hibernate.cfg.Configuration;

public class HibernateAnnotation {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static void registerEntities(Configuration configuration) {
        configuration.addAnnotatedClass(Member.class);
        configuration.addAnnotatedClass(Membership.class);
    }

}
