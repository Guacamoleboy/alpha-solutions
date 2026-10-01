# TS-11 – CI quality gate

## Hvad der allerede var på plads

Testmiljøet i backend var allerede konfigureret:

- `ATest` vælger testmiljøet (`set.env=test`) og opretter Hibernate's test `EntityManagerFactory`.
- `DotEnv` læser `.env.test` fra `backend/src/main/resources`.
- `HibernateProperties.setTestProperties` peger på PostgreSQL 16.2 via Testcontainers JDBC og bruger `create-drop`.
- Maven har JUnit 5, Testcontainers JDBC/PostgreSQL og Surefire konfigureret.

Derfor behøvede CI ikke en separat PostgreSQL-service eller nye testafhængigheder. Testcontaineren startes af JDBC-driveren, når testene kører.

## Tilføjelse

`.github/workflows/backend-ci.yml` starter ved pull requests mod `main` og kan også startes manuelt. Workflowet bruger Java 17 fra `backend/pom.xml`, kører `mvn verify` fra backend-mappen og analyserer Java-koden med CodeQL. Hvis byg, tests eller CodeQL fejler, fejler jobbet `Backend CI`. CodeQL er sat op i Java's `none` build mode, så workflowet ikke bygger Maven-projektet to gange.

At køre fra `backend/` er vigtigt, fordi testopsætningen indlæser `.env.test` med en sti relativ til backend-mappen. Testene kræver Docker; GitHub-hosted Ubuntu runneren leverer Docker til Testcontainers.

`.env.test` er ignoreret af Git og følger derfor ikke med i GitHub-checkout. CI-steppet får de nødvendige testværdier som environment variables. Kun `JWT_SECRET` hentes fra repository secret `TEST_JWT_SECRET`; værdien skal være en separat testnøgle og må aldrig genbruges i produktion. Databaseværdier skal ikke oprettes som secrets, fordi Testcontainers JDBC starter testdatabasen automatisk.

CodeQL-upload kræver, at GitHub code scanning er tilgængelig for repository'et (for eksempel et offentligt repository eller Code Security på organisationens plan).

## Det, der mangler uden for kodebasen

Workflowfilen kan ikke selv demonstrere faktiske succesfulde og fejlede runs eller gøre et statuscheck påkrævet ved merge. Efter workflowet er tilføjet til GitHub:

1. Åbn en pull request mod `main`, kontrollér at jobbet `Backend CI` gennemfører en grøn kørsel, og gem linket som dokumentation.
2. Demonstrér en rød kørsel ved at lade en reel test eller obligatorisk analyse fejle i en separat pull request; ret fejlen og kontrollér derefter en grøn kørsel. Tilføj links til begge runs her.
3. Opret eller opdatér branch protection/ruleset for `main`, så `Backend CI` er et påkrævet statuscheck, og pull requests ikke kan merges, mens det fejler eller mangler.

Disse tre punkter afhænger af GitHub-kørsler og repository-indstillinger og er derfor ikke markeret som gennemført i TS-11 endnu.
