# TS-09 – Testcases for US-11 og US-04

## Valg af user stories

US-11 (nulstilling af glemt adgangskode) er valgt, fordi identitetskontrol og ændring af loginoplysninger kan give en bruger adgang til en forkert konto eller forhindre adgang til egen konto. US-04 (ejerens administrationsportal) er valgt, fordi portalen samler flere administrative funktioner, og adgang skal begrænses til ejerrollen. Tilsammen dækker historierne både en følsom medlemsrejse og en central rollebeskyttet del af systemet.

Casene nedenfor er testdesignforslag, der kan automatiseres på det billigste relevante niveau efter TS-08. De verificerer kun krav, der står i historierne; de indfører ikke fx en bestemt minimumslængde for adgangskoder eller en bestemt fejltekst, som historierne ikke kræver.

## US-11 – Reset a Forgotten Password

Testdata: En syntetisk medlemskonto med kendt e-mail, fødselsdato og adgangskode. Brug en separat konto til negative cases. Kør ændringer mod testdatabasen, aldrig mod rigtige medlemskonti.

| ID | Acceptkriterium | Teknik / data | Handling | Forventet resultat |
|---|---|---|---|---|
| U11-01 | AC1 | Ækvivalensklasse: login-siden | Åbn login-siden og vælg “Forgot password”. | Flowet til nulstilling åbnes. |
| U11-02 | AC2–3 | Ækvivalensklasser: korrekt kontopar / forkert kontopar | Indtast kontoens e-mail og præcise fødselsdato; gentag med en e-mail, der ikke findes, og med korrekt e-mail men en anden dato. | Kun det korrekte par giver adgang til formularen til ny adgangskode. De forkerte par giver ikke adgang. |
| U11-03 | AC2–3 | Grænseværdi: fødselsdato lige ved siden af den korrekte dato | Indtast den korrekte e-mail og fødselsdatoen dagen før eller efter kontoens dato. | Kontrollen afviser parret; nærliggende datoer må ikke blive accepteret som et match. |
| U11-04 | AC4–6 | Beslutningstabel: bekræftelsesværdier | Se tabel 1. | Adgangskoden ændres kun, når de to indtastninger er ens; ved forskel vises en tydelig fejlbesked. |
| U11-05 | AC5, AC8 | Ækvivalensklasse: vellykket nulstilling | Nulstil adgangskoden med to ens værdier, log derefter ind med den nye adgangskode. | Nulstillingen lykkes, og login med den nye adgangskode virker. |
| U11-06 | AC8 | Ækvivalensklasse: gammel adgangskode efter nulstilling | Efter U11-05 forsøges login med den tidligere adgangskode. | Den gamle adgangskode giver ikke adgang. |
| U11-07 | AC7 | Afhængighedskontrol | Gennemfør nulstillingsflowet i et testmiljø uden opsat e-mailafsendelse. | Nulstillingen kan gennemføres uden e-mailafsendelse. |

### Tabel 1 – beslutningstabel for adgangskodebekræftelse

| Regel | Ny adgangskode indtastet | Gentaget adgangskode indtastet | Værdier ens | Forventet resultat |
|---|---|---|---|---|
| R1 | Ja | Ja | Ja | Adgangskoden nulstilles. |
| R2 | Ja | Ja | Nej | Nulstilling afvises, og en tydelig besked vises. |
| R3 | Nej eller tom | Nej eller tom | Ikke relevant | Ingen succesfuld nulstilling. Formularen må ikke behandle manglende værdier som en bekræftet ny adgangskode. |

R3 er en grundlæggende ugyldig-input-case; den præcise valideringstekst er ikke fastsat i US-11.

## US-04 – Owner Administration Portal

Testdata: En ejerkonto, en konto uden ejerrolle og en anonym browser/session. Brug en tilgængelig administrativ funktion i testmiljøet til feedback-casene. Test rollebegrænsning både via normal navigation og ved direkte URL, så skjulte links ikke forveksles med adgangskontrol.

| ID | Acceptkriterium | Teknik / data | Handling | Forventet resultat |
|---|---|---|---|---|
| U04-01 | AC1, AC4 | Ækvivalensklasser: ejer / bruger uden ejerrolle / anonym | Log ind som hver af de tre sessionstyper og åbn portalens rute. | Ejer får adgang; bruger uden ejerrolle og anonym bruger får ikke adgang. |
| U04-02 | AC1, AC4 | Grænse/adgangskontrol: direkte navigation | Log ind som ikke-ejer og åbn en administrativ URL direkte, uden først at besøge portalen. | Adgangen afvises også ved direkte URL. |
| U04-03 | AC2–3 | Ækvivalensklasse: tilgængelige administrative funktioner | Log ind som ejer, gennemgå portalens administrative funktioner, og navigér mellem dem via portalens navigation. | Ejeren kan åbne de tilgængelige funktioner og navigere mellem dem fra portalen. |
| U04-04 | AC5 | Brugerrejse | Som ejer, udfør en administrativ handling gennem brugerfladen. | Handlingen kan udføres uden direkte adgang til backend eller database. |
| U04-05 | AC7 | Beslutningsregel: handling lykkes | Udfør en gyldig administrativ handling i testmiljøet. | Ejeren får passende feedback om succes. |
| U04-06 | AC8 | Beslutningsregel: handling afvises | Udfør en handling med data, som den valgte funktion afviser, eller simulér en afvisning i testmiljøet. | Ejeren får passende feedback om, at handlingen ikke kunne gennemføres. Ingen succesfeedback vises. |

### Tabel 2 – beslutningstabel for portaladgang

| Regel | Logget ind | Rolle er OWNER | Forventet resultat |
|---|---|---|---|
| R1 | Ja | Ja | Portal og tilgængelige administrative funktioner kan åbnes. |
| R2 | Ja | Nej | Portal og administrative funktioner afviser adgang. |
| R3 | Nej | Ikke relevant | Portal og administrative funktioner afviser adgang. |

## Testdata og afgrænsning

- Brug syntetiske konti med kendte roller, fødselsdatoer og adgangskoder. De negative cases ændrer én relevant værdi ad gangen, så årsagen til et udfald er tydelig.
- Brug gyldige og afviste administrative handlinger fra testmiljøets eksisterende funktioner; US-04 fastlægger ikke en bestemt formular eller fejltype.
- Vælg teknikker efter relevans: ækvivalensklasser til gyldige/ugyldige konto- og rollegrupper, grænsetilfælde til næsten-matchende fødselsdato og beslutningstabeller til kombinationer af adgangskodebekræftelse og portaladgang.
- Der er ikke angivet numeriske adgangskodekrav i US-11, så der kan ikke udledes længdegrænser til Boundary Value Analysis uden et særskilt produktkrav.