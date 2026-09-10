# ADR-003: Authentication

## Status
Approved

## Context
The application needs a validation mechanism so people can log in and access places they need to access.

## Decision
We are going to use JWT for authentication.
The backend will validate the JWT token.

## Alternatives
Session / Local Storage

Not using that as it's inefficient and not compliant with EU regulations with safe data handling.

## Consequences
- People can log in using their information and get a JWT token in order to get access and or stay on our services.
- The frontend must handle the JWT token in a secure manner.
- JWT configuration and management must be dealt with.