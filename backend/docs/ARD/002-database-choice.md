# ADR-002: Database Choice

## Status
Awaiting team approval

## Context
Our application needs a place to store its data. The database should work with Java and be able to store needed information
and the needed size of data.

## Decision
We are going to use PostgreSQL as that is what we are most comfortable with as a group.

## Alternatives
MySQL
MongoDB
MariaDB

All rejected as the team focuses on completing tasks instead of extending knowledge.

## Consequences
- The application uses a reliable database system that is able to handle the information it needs to.
- The frontend user input has a place to be stored.
- PostgreSQL works well with Java.
- Developers need a local environment set up for development.