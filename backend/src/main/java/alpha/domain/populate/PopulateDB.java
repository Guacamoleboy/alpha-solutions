package alpha.domain.populate;

import alpha.domain.membership.entity.Membership;
import alpha.domain.membership.service.MembershipService;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class PopulateDB {

    // Attributes

    // _________________________________________________________________________________________________________________

    public static void populate(EntityManager em) {

        // Instantiate
        MembershipService membershipService = new MembershipService(em);

        // Clean DB
        membershipService.deleteAllSafe();

        // Memberships
        Membership free = Membership.builder()
                .name("Free")
                .price(BigDecimal.valueOf(0))
                .currency("DKK")
                .active(true)
                .build();
        Membership basic = Membership.builder()
                .name("Basic")
                .price(BigDecimal.valueOf(99))
                .currency("DKK")
                .active(true)
                .build();
        Membership premium = Membership.builder()
                .name("Premium")
                .price(BigDecimal.valueOf(199))
                .currency("DKK")
                .active(true)
                .build();
        Membership superPremium = Membership.builder()
                .name("Super Premium")
                .price(BigDecimal.valueOf(299))
                .currency("DKK")
                .active(true)
                .build();

        // Persist
        membershipService.create(free);
        membershipService.create(basic);
        membershipService.create(premium);
        membershipService.create(superPremium);

    }

}
