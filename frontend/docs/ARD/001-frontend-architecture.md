# ADR-002: Frontend Architecture

## Status
Awaiting team approval

## Context
The frontend application needs architecture that's easy to maintain and must be something we as a development team already have knowledge
about due to our size.

## Decision
We are going to be using JavaScript, React & Vite.

## Alternatives
TypeScript

Rejected as team focuses on completing the task instead of implementing unneccesary complexity.
Rejected due to different skill levels in the development team.

## Consequences
- Our application will be easy to maintain as React is a very large open source community.
- Some dependencies will become deprecated which is why dependencies should be picked with care.
- JavaScript offers a simple solution both developers know.
- Vite allows easy, quick and efficient build.