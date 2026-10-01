# Teststrategi

## Formål

Testindsatsen skal fange væsentlige fejl uden at gøre hver ændring dyr at udvikle og vedligeholde. Vi tester derfor ikke den samme CRUD-adfærd på DAO-, service- og controllerlaget. Testniveau vælges efter, hvor en fejl kan opstå, og hvor den kan opdages billigst.

## Risici for systemet

- Forkert lagring eller hentning af data, herunder relationer og egne databaseforespørgsler.
- Fejl i centrale regler for booking, banekonflikter, åbningstider, medlemskab og adgangsroller.
- API-fejl, hvor ruter, adgangskontrol, statuskoder eller JSON-format ikke svarer til klientens forventninger.
- Fejl i de vigtigste brugerforløb, når frontend og backend bruges sammen.

## Testniveauer

### DAO og database

`EntityManagerDAOTest` er den fælles kontrol af generisk CRUD. Vi opretter ikke de samme CRUD-tests igen for hver DAO, service eller controller. En separat DAO-test er relevant, når DAO'en har specialiseret forespørgselsadfærd, som den generiske test ikke dækker, for eksempel filtrering eller kontrol af tidsmæssigt overlap.

De eksisterende DAO-tests bruger PostgreSQL via Testcontainers. Det giver mere tillid til databaseadfærd end mocks, samtidig med at testene kan køres isoleret fra en udviklers lokale database.

### Service og forretningsregler

Vi tester ikke service-metoder alene, fordi de kalder en DAO. Hvis en service blot videresender et CRUD-kald, er DAO-testen normalt tilstrækkelig. Vi tilføjer kun en service- eller use-case-test, når laget indeholder en selvstændig regel eller samler flere afhængigheder på en måde, som ikke dækkes lavere nede. Eksempler er afvisning af en booking uden for åbningstid eller håndtering af en konflikt mellem booking og eventreservation.

I sådanne tests foretrækkes den rigtige service og DAO sammen med testdatabasen, hvis opsætningen er enkel. En mock kan bruges til en isoleret regel, når den gør testen væsentligt enklere. Hvis testen kræver mange mocks eller gentager intern kaldsrækkefølge, er det et tegn på, at testen bør forenkles eller at designet bør vurderes, frem for at bygge et stort mock-setup.

### Controller og API

Controller-tests skal ikke gentage DAO'ens CRUD- eller service-regeltests. Behold kun få API-tests for forhold, der hører til ved HTTP-grænsen: at en vigtig rute virker, at rolle/adgang bliver håndhævet, og at klienten får forventet status og svarformat. De eksisterende tests bør vurderes efter dette formål; tests, der kun gentager allerede dækket CRUD uden at kontrollere HTTP-adfærd, giver lav ekstra værdi.

### UI og end-to-end

Automatisér kun et lille antal kritiske brugerforløb gennem hele systemet. Browserbaserede tests kan bruge Selenium, hvis værktøjet vælges til UI-testarbejdet. Selenium er ikke nødvendig for backend-testene og skal ikke indføres alene for at opfylde en testpyramide. UI-tests supplerer lagene nedenunder; de erstatter ikke hurtigere DAO- eller regeltests.

## Automatisering og manuel test

- Automatisér gentagelige checks af CRUD, særlige DAO-forespørgsler, udvalgte forretningsregler og kritisk API-adfærd.
- Automatisér kun de vigtigste komplette brugerrejser i browseren, da end-to-end-tests er langsommere og mere følsomme over for ændringer.
- Brug manuel, udforskende test til visuel kvalitet, brugervenlighed og ændringer, hvor få automatiske tests giver rimelig dækning.
- Test ikke alle kombinationer på alle lag. Vælg grænsetilfælde og fejlscenarier ud fra risikoen for brugeren og konsekvensen af fejlen.

## Praktiske principper

- Genbrug `EntityManagerDAOTest` som fælles CRUD-dækning.
- Skriv specialiserede tests dér, hvor den relevante adfærd bor; undgå at teste samme resultat tre gange gennem lagene.
- Brug testdata med tydelige datoer, tider og roller, og sørg for at testene ikke afhænger af en bestemt lokal database eller delt, forudfyldt data.
- Når en fejl findes, tilføj den mindst omfattende test, der reproducerer fejlen og beskytter adfærden fremover.