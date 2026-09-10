---
name: review-contributing
description: Review the current repository changes against CONTRIBUTING.md and provide advisory feedback without modifying files or generating implementation code.
---

# Review CONTRIBUTING Guidelines

Use this skill when the developer wants to check their current changes against the project's `CONTRIBUTING.md`.

## Purpose

This skill provides **advisory input** to the developer.

The AI reviews the developer's changes, identifies potential conflicts with the project's contribution guidelines, and raises questions for the developer to consider.

The developer remains responsible for:
- deciding whether a finding is valid
- deciding what action to take
- implementing any changes
- deciding whether the work is ready for commit or pull request

## Strict restrictions

This skill MUST NOT:

- modify any files
- create new files
- delete files
- rename or move files
- generate replacement implementation code
- implement suggested fixes
- generate unit tests or other tests
- automatically apply fixes
- make the final engineering decision
- claim that AI approval means the code is compliant

This skill MAY:

- inspect repository files
- inspect Git status
- inspect Git diffs
- inspect existing code for context
- inspect `CONTRIBUTING.md`
- identify potential violations
- explain which rule is relevant
- ask questions
- identify assumptions or areas requiring human judgment

---

# Review procedure

## 1. Read CONTRIBUTING.md

Read the repository's `CONTRIBUTING.md` before reviewing the changes.

Treat the current repository version of `CONTRIBUTING.md` as the authoritative source for contribution rules.

Do not invent additional project rules.

Existing code may be used as supporting context, but it must not override an explicit rule in `CONTRIBUTING.md`.

## 2. Inspect Git state

Inspect:

- current branch
- working tree status
- staged changes
- unstaged changes
- commits on the current branch where relevant

Determine the appropriate base branch.

According to `CONTRIBUTING.md`, feature work should be created from `development`.

If `development` exists, prefer comparing the feature branch against `development` rather than `main`.

## 3. Determine the change set

Review the changes introduced by the current feature branch relative to its base branch.

Also inspect staged and unstaged changes so uncommitted work is not silently ignored.

Use Git information to distinguish:

- existing code
- branch changes
- staged changes
- unstaged changes

If the complete change set cannot be determined reliably, state this in the review.

Do not report unrelated pre-existing issues unless the current change directly affects them.

---

# CONTRIBUTING checks

Only check rules that are relevant to the actual changes.

## 1. Naming and Formatting

### General

Check that:

- code and user-facing project content are in English unless it is an internal PO log
- names are clear and descriptive
- unnecessary abbreviations are avoided

Do not treat normal technical terminology as an abbreviation violation without reasonable evidence.

### Java

Check that:

- classes use `PascalCase`
- methods use `camelCase`
- variables use `camelCase`
- constants use `UPPER_SNAKE_CASE`

### JavaScript

Check that:

- variables use `camelCase`
- functions use `camelCase`
- React components use `PascalCase`
- constants use `UPPER_SNAKE_CASE`

### Markdown

Check that:

- parent/child node layout is respected where applicable
- content is clear and professional

---

## 2. Folder Structure

Check whether changed files are placed according to the documented project structure.

### Backend

Pay particular attention to:

- `config/`
- `controller/`
- `dto/`
- `dao/`
- `entity/`
- `exception/`
- `route/`
- `server/`
- `service/`
- `util/`

under the documented `backend/src/main/java/alpha/` structure.

### Frontend

Pay particular attention to:

- `app/pages/`
- `app/routes/`
- `app/layout/`
- `app/App.jsx`
- `app/main.jsx`
- `feature/component/`
- `shared/style/`
- `shared/data/`
- `shared/components/`

For feature components, check the documented naming/layout convention:

- `Component.jsx`
- `Component.hooks.js` when needed
- `Component.module.css`

Only flag structural issues when the purpose of the changed file is reasonably clear.

---

## 3. Branch Rules

Check the current branch.

Flag working directly on:

- `main`
- `development`

as a clear violation.

Where possible, determine whether the current branch was created from `development`.

If the branch origin cannot be established reliably, report this as something to verify rather than claiming a violation.

---

## 4. Commit Messages

If commits are part of the reviewed change set, check whether their messages are:

- short
- clear
- professional
- descriptive of what was done

Do not expect the commit message to contain the depth of the Pull Request.

Do not review unrelated historical commits.

---

## 5. Pull Requests

A local review cannot automatically verify every GitHub Pull Request property.

If GitHub CLI or another available GitHub integration provides access to the relevant PR, inspect it where useful.

Otherwise list relevant PR requirements as verification items rather than violations.

Check, where possible:

- PR has a title
- PR targets `development`, unless it is a main release
- PR follows the repository's Pull Request template
- another developer can approve the PR before code enters the codebase
- all CI checks pass

Never claim that CI passed merely because this skill completed successfully.

---

## 6. DTO

Treat the DTO rules as an important project convention.

Check that:

- database IDs or other critical information are not exposed through DTOs
- request and response DTOs are separated
- DTO names follow the corresponding entity names

Expected naming convention:

```text
Member
MemberRequest
MemberResponse
```

Do not make independent legal or GDPR conclusions.

The task is to check whether the implementation follows the DTO rules documented by the project.

---

## 7. Validation

Check whether validation happens before service or internal business logic according to the project's documented convention.

Check whether validation errors are:

- consistent
- professional
- appropriate for the existing application behavior

If the correct validation boundary cannot be established from the available code, ask the developer a question instead of declaring a violation.

---

## 8. Exception Handling

Check whether changed Java code follows the project's exception-handling convention.

The project uses Java exception handling and communicates relevant customer-facing feedback through a notification UI element.

Do not invent exception classes, architectural patterns, or implementation requirements that are not established elsewhere in the repository.

---

# Findings

For every finding, use the following format:

### [Severity] — [Short title]

**Location:** `path/to/file:line` when available

**Rule:**  
The relevant rule from `CONTRIBUTING.md`.

**Observation:**  
What the changed code currently does.

**Why it matters:**  
Why this may conflict with the project's documented convention.

**Developer question:**  
A question the developer should consider before deciding whether action is required.

Do not provide replacement code.

## Severity levels

Use only these severity levels:

### Violation

Use when the change clearly conflicts with an explicit rule.

### Warning

Use when the change likely conflicts with a rule, but context may change the conclusion.

### Consideration

Use when something deserves attention but there is insufficient evidence to call it a violation.

### Language exceptions

The general language rule is English unless content is intended as internal PO logs.

Do not report language violations for documentation under:
- `docs/reflections/`
- `docs/work-log/`

These directories may contain Danish or other project-reflection/work-log content.

When reviewing Markdown in these directories:
- do not flag Danish text as a CONTRIBUTING violation
- still check clarity and professionalism where the Markdown structure is relevant
- do not rewrite or translate the content

---

# Verification items

Include a `Verification items` section for relevant requirements that cannot reliably be checked locally.

Examples:

- PR target branch
- PR title
- PR template
- required human approval
- GitHub CI status

Do not list verification items that are irrelevant to the current change.

---

# Questions for the developer

After the findings, provide concise questions that help the developer evaluate the feedback.

Questions should encourage the developer to reason about the change.

Do not answer the questions for them.

---

# Output format

Always return:

```text
# CONTRIBUTING Review

## Scope

...

## Findings

...

## Verification items

...

## Questions for the developer

...

## Summary

...
```

If there are no findings:

```text
No CONTRIBUTING violations identified.
```

If potential issues exist:

```text
Potential CONTRIBUTING violations identified — developer review required.
```

If the review cannot reliably determine the change set:

```text
Unable to complete the review reliably — additional Git context is required.
```

The summary MUST NOT imply that the AI has approved the code.

---

# Final constraint

After completing the review, stop.

Do not offer to:

- fix the issues
- modify files
- generate implementation code
- generate tests
- apply changes
- commit changes