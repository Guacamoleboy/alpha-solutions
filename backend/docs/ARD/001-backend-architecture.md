# ADR-001: Backend Architecture

## Status
Awaiting team approval

## Context
The application requires a backend that can provide the needed functionality and implementation.
The backend should be easy to maintain, text and extend in case of additional functionality.

## Decision
We will use TEST API as the architecture and solution for this specific application. It'll be the communication between
the frontend layer and the backend layer.

The backend folder structure will be:

- controller/ - Handles HTTP request and responses
- service/ Handles logic needed from the HTTP requests or internal logic
- entity/ - Contains entities and their logic
- dto/ - Contains data transfer objects
- exception/ - Custom Java exceptions needed to run the application
- config/ - Application configs
- server/ - Javalin server setup
- util/ - Application utility classes
- route/ - Provided API endpoints

## Alternatives
GraphQL

Not using GraphQL as the team struggles as a 2-man group already while learning a new system for some of the team members
will cause unneeded stress.

## Consequences
- The frontend and backend are able to talk to each other.
- Data is secure and stored in an external layer.
- New endpoints and functionality can be added and adjusted without need of a massive refactor.
- Clear SoC.