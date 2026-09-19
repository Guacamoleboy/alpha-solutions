---
name: analyze-user-story
description: Analyzes a user story from a user-provided file path against the teacher's expectations and reports what should change, why, and how. Never edits the user's files.
---

# Analyze User Story

Analyze the user story at the file path provided by the user.
Do not modify any files. The user makes all changes manually.

## Before Analysis

1. Read the user story at the provided path.
2. Inspect relevant project files when needed to understand the domain or current implementation.
3. Find and read available teacher feedback, assignment requirements, rubrics, examples, or project documentation in the repository.
4. Use documented teacher expectations as the primary source of truth.
5. If teacher expectations cannot be found, state that clearly and evaluate only against available project requirements and common user-story conventions.

Do not invent teacher requirements or business rules.

## Teacher Expectations

When supported by the available teacher feedback, verify that the user story follows these expectations:

- It describes user-facing functionality and business value, not internal implementation or refactoring.
- It represents a complete vertical slice of functionality.
- It is written in non-technical language that a real user or stakeholder from the business domain could reasonably use.
- It clearly identifies a real user role, what that user wants, and why they want it.
- Prefer the structure: `As a <role>, I want <goal>, so that <value>.`
- The `so that` clause must communicate meaningful user or business value.
- The title describes the user-facing capability and avoids technical wording such as `logic`, `REST API`, `DTO`, `database`, or `refactor`.
- Acceptance criteria are specific, observable, and testable.
- Acceptance criteria define concrete business rules when those rules are known.
- Acceptance criteria describe expected behavior rather than implementation details.
- Testing, unit tests, code quality, documentation, pull requests, merging, and similar completion requirements belong in the Definition of Done unless they are explicitly part of the feature requirement.
- Technical implementation details should be handled by Technical Stories or implementation tasks when appropriate.
- Do not treat future or unspecified functionality as if it is already a defined requirement.
- Do not invent thresholds, subscription rules, permissions, limits, or other business rules that have not been defined.

## Analyze

Check whether the user story:

- Matches documented teacher expectations.
- Has a clear and appropriate user role.
- Has a concrete user-facing goal.
- Explains why the functionality provides value.
- Represents one coherent vertical slice.
- Uses non-technical, domain-appropriate language.
- Has a clear title describing the user-facing capability.
- Has specific, observable, and testable acceptance criteria.
- Includes known business rules and constraints where relevant.
- Avoids implementation details that belong in Technical Stories or tasks.
- Avoids Definition of Done requirements inside acceptance criteria.
- Matches actual project and domain terminology.
- Is consistent with the current implementation where relevant.
- Contains missing, vague, conflicting, duplicated, unnecessary, or invented requirements.

Only report meaningful issues.

## Response

Start with one conclusion:

- `Looks good` if no meaningful changes are needed.
- `Changes recommended` if something should be adjusted.

For every recommended change, use:

### What

State exactly what should be changed.

### Why

Explain which teacher expectation, project requirement, or user-story principle it conflicts with.

### How

Explain concretely how the user should change it without rewriting the entire story.
When useful, reference the relevant teacher feedback, requirement, rubric, documentation, or project file supporting the recommendation.

## Rules

- Never edit the user story or project files.
- Never rewrite the complete user story unless explicitly asked.
- Never invent teacher expectations or business rules.
- Distinguish documented teacher requirements from general recommendations.
- Prioritize teacher feedback and assignment requirements over generic best practices.
- Do not report harmless stylistic preferences as errors.
- Do not require technical details in a User Story merely because they exist in the implementation.
- Treat User Stories and Technical Stories as separate concerns.
- Keep the final analysis concise, specific, and actionable.