---
name: user-story-compliant
description: Reviews a specific User Story against the implementation diff and reports missing or extra scope. Use after a code session to check compliance with the User Story. Created by Guacamoleboy.
---

# User Story Compliance Review

Review one specific User Story against the implementation that was changed for it. This is a read-only review: never modify source code, User Stories, tests or Git state.

## Required User Story Identifier

Before reviewing anything, require the prompter to specify the User Story as an exact standalone identifier in the format `US-01`, `US-02`, and so on.

- Accept only `US-` followed by two digits, provided on its own as the User Story reference.
- Reject formats such as `US:01`, `US-1`, `US-01: <Name>`, `US-01 <Name>`, or any other variation with a name or additional text attached.
- If the identifier is missing or invalid, stop the review and ask the prompter to provide only the valid identifier, for example `US-01`.

## Branch Safety

Before reviewing the diff, check the current branch with `git branch --show-current`.

- If the current branch is `development`, stop the review before inspecting compliance results.
- Tell the prompter that the changes are currently on `development` and ask whether a new branch should be created containing the changes made during the session, so the changes can be compared against `development`.
- Do not create, move or commit branches automatically. Continue only after the prompter explicitly confirms the branch action and the changes are available on a non-`development` branch.

## Review

1. Read the specified User Story, including its acceptance criteria and sub-issues.
2. Compare the relevant implementation changes with the `development` branch:
   - Prefer `git diff development...HEAD` for committed changes.
   - Also inspect `git diff` and `git diff --cached` for uncommitted changes.
   - If the local `development` branch is unavailable, use `origin/development` when available and state which base was used.
3. Map each acceptance criterion to concrete evidence in the diff and current implementation.
4. Identify only meaningful deviations:
   - Missing: an acceptance criterion is not implemented or is only partially implemented.
   - Extra: user-facing functionality or behavior was added that is not required by the User Story and is not necessary to support its criteria.
5. Do not classify necessary technical implementation, tests, styling, error handling or supporting code as extra scope merely because it is not written in the User Story.
6. Do not invent requirements. If the User Story is ambiguous, state the ambiguity briefly instead of treating an assumption as a failure.

## Response

Keep the response short and specific. Use this format:
`Status: Compliant` if nothing meaningful is missing or extra.

### Otherwise use:

`Status: Changes needed`

### For each finding:

- `Missing: <what is missing>. Why: <which acceptance criterion is not fulfilled and why it matters>.`
- `Extra: <what was implemented beyond the User Story>. Why: <why it is outside the requested scope>.`

Mention the relevant acceptance criterion or changed area for every finding. Do not provide a long general code review, refactoring advice or implementation plan.
If no relevant implementation diff exists, report that clearly instead of declaring the User Story compliant.