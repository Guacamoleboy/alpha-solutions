# TS-10 – Testability assessment

## Scope

This assessment focuses on `BookingService`, one existing backend service with central booking rules: valid time range, member and court eligibility, operating hours, event reservations, court closures and overlapping bookings. This is a deliberately selected example to demonstrate testability improvements; it is not an assessment or refactor of every service in the system.

## Dependencies and findings

`BookingService` uses:

- `BookingDAO` for booking persistence and booking-specific queries.
- `EventCourtReservationDAO` and `CourtClosureDAO` to check availability.
- `CourtService`, `MemberService` and `OperatingHourService` to resolve the related domain data.
- `EntityManagerService` for inherited CRUD operations.

Before this change, the constructor received the `EntityManager` and three services, but constructed all three DAOs internally. That direct construction coupled the business service to concrete persistence setup. A focused test could not supply controlled DAO behavior without changing the service or relying on the real database. The code already uses constructor injection for the other services, so introducing a dependency injection framework or broad interface layer would add unnecessary architecture for this case.

## Change made

`BookingService` now receives its three DAOs through its constructor along with its existing service dependencies. `BookingRouting` and `PopulateDB` act as composition points: they create the real DAOs from the shared `EntityManager` and pass them into the service. Production behavior and the chosen persistence implementation remain explicit at those composition points.

## How this helps testing

A focused test can now construct `BookingService` with controlled DAO test doubles and service collaborators, then exercise one rule such as rejecting an overlapping event reservation. The test can control whether the overlap query returns `true` without constructing a database-backed DAO or modifying `BookingService` internals. When several collaborators would require a large mock setup, the existing PostgreSQL/Testcontainers integration approach is the simpler choice; this refactor does not require us to create a large mock suite.

The change improves the seam for a small, selected test without adding a mocking library, container framework, or application-wide dependency injection system. No new test suite was added as part of this assessment.

## Verification limits

The constructor wiring was updated at both current creation sites. Per the project's current test-running constraint, no tests or build were run; the diff was reviewed with `git diff --check`. The focused service test can be added when that booking rule is selected for test coverage.