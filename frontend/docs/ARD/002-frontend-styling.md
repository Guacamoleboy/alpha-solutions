# ADR-002: Frontend Styling

## Status
Awaiting team approval

## Context
Our application needs an identity. In order to make it simple we need a simple and efficient way to handle mistakes and comply with equal visuals.

## Decision
We are going to be using .module.css files in order to style components to their needs while keeping globals.css as styling for the application.

## Alternatives
Everything goes into globals.css

Rejected as it becomes Spaghetti and confusing over time.

## Consequences
- More files. More keeping track of.
- Files are always the same though, so once you learn it you know where the styling is at.
- A little more knowledge compared to normal html / css styling.