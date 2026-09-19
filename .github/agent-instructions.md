# Alpha Solution Agent Instructions

## Project Overview

Alpha Solution is about using Scrum as a development team and working in Sprints throughout the course. We will progress using Scrum techniques and try to integrate AI into our Scrum workflow.

### Root Folders

- `.github/`: CI / CD, Workflows, Agents and other important tasks related to GitHub and Agents
- `docs/`: Files and important project documentation
- `frontend/`: Portfolio website for AIDA
- `backend/`: Java REST App for Rubric and client learning purposes
- `.agents/skills/`: Agent Skills specific to this Repository and Project.

### Core Architecture (`frontend/` folder)

- `frontend/node_modules/` - Installed npm dependencies. Do not modify files in this directory manually.
- `frontend/public/` - Static assets that are served directly by the frontend.
- `frontend/src/` - Main frontend source code.
- `frontend/src/app/` - Pages, layout, access, roles and entry point for the portfolio.
- `frontend/src/features/` - Components that are page specific only.
- `frontend/src/features/any-page/` - Components are page based.
- `frontend/src/shared/` - Used for shared context across the portfolio. Globals, types, styling and misc.
- `frontend/src/shared/hooks/` - Custom hooks to prevent redundant code across the app. Such as useAuth, useForm and so on.
- `frontend/src/shared/utils/` - Shared utility classes and functions to prevent redundant implementations.
- `frontend/src/shared/context/` - Context files that are added to reduce the need for parent / child behavior when a component tree becomes too big.

### Java Backend Architecture (`backend/` folder)

- `backend/src/` - Main application folder. Like any other Java application. Maven.
- `backend/.idea/` - Don't adjust manually.
- `backend/.mvn/` - Don't adjust manually.
- `backend/docs/` - Folder for documentation, learning purposes and general personal knowledge.
- `backend/target/` - Don't adjust manually.
- `backend/src/main/resources/` - Java-specific resources including things such as rubric, prompts, student reports and env files. Never expose or leak environment files or secrets.
- `backend/src/main/java/alpha/` - The Java application with `Main.java` as its entry point.

## Agent Permissions and Workflow

### Read-Only Actions

The Agent may perform read-only operations without asking for permission.

This includes:

- Searching the repository with `grep`, `rg`, `find`, or equivalent tools
- Listing directories and files
- Reading source code, configuration files, documentation, logs, and test files
- Inspecting Git history, diffs, status, and branches
- Following imports, references, and usages across the repository
- Running commands that only inspect or analyze the repository and do not modify files, repository state, or persistent project data

Do not ask for confirmation before performing read-only investigation.

### Changes Require Approval

Ask for approval before performing any action that modifies files, dependencies, Git state, repository state, or persistent project data.

This includes:

- Creating, editing, moving, renaming, or deleting files
- Installing, updating, or removing dependencies
- Running formatters or tools that modify files
- Running database migrations
- Committing, rebasing, merging, resetting, or otherwise modifying Git state
- Changing configuration or environment files
- Running scripts or commands that may modify project files or persistent data

Before requesting approval, investigate the relevant code using read-only operations so the proposed change is based on the actual codebase.

When requesting approval, briefly state what will be changed, which files are expected to change, and why the change is necessary.

## Finding Related Code

1. **Search for concepts**: Use semantic or file search when locating code by behavior or general concepts.
2. **Grep for exact strings**: Use `rg`, `grep`, or equivalent tools for error messages, identifiers, function names, imports, and other exact strings.
3. **Follow imports and usages**: Check definitions, imports, references, and callers of the relevant code.
4. **Check test files**: Tests often reveal usage patterns and expected behavior.

Before modifying code, inspect the relevant files and surrounding implementation. Do not assume a file, function, API, dependency, or configuration exists. Verify it first.

## Validating TypeScript Changes

- `npm run typecheck-client` for the main sources under `frontend/`.
- Run the narrowest relevant validation for the changes made.
- Run relevant existing tests when applicable.
- Do not run or ask the user to run `npm run build` unless the user explicitly requests a build.
- Report validation failures clearly instead of hiding, bypassing, or silently ignoring them.

## Coding Guidelines

### Indentation

Use tabs, not spaces.

### Naming Conventions

- Use PascalCase for React components and component files, e.g. HomePage, UserCard, and NavigationMenu.
- Use PascalCase for classes, interfaces, and type aliases, e.g. UserProfile, ApiResponse, and AuthState.
- Use camelCase for variables, function names, parameters, and object properties, e.g. userProfile, getUserData, and isAuthenticated.
- Use camelCase for utility functions, hooks, and non-component modules, e.g. formatDate, useAuth, and fetchPortfolioData.
- Use whole words in names when possible. Avoid unnecessary abbreviations such as usr, btn, or cfg.

### Types

- Do not export `types` or `functions` unless they need to be shared across multiple components or modules.
- Do not introduce new `types` or `values` to the global namespace.
- Avoid `any`. Use explicit types whenever practical.
- Use `unknown` when a value is genuinely not known at compile time, and narrow it before use.

### Comments

- Do not add comments unless they are necessary to explain important or non-obvious behavior. Comments in the codebase should generally be written by the developer rather than the Agent.

### Scope Discipline

- Make the smallest change necessary to complete the requested task.
- Do not refactor unrelated code.
- Do not rename or reorganize files unless required by the task.
- Preserve the existing architecture and conventions unless the task explicitly requires changing them.
- Do not fix unrelated issues discovered during the task. Mention them separately instead.

### Code Quality

- All `.tsx`, `.js`, `.ts`, `.jsx` files must include a pathing header.
- Prefer `async` and `await` over `Promise` and `then` calls.
- Look for existing test patterns before creating new structures.
- If you create any temporary new files, scripts, or helper files for iteration, clean up these files by removing them at the end of the task.
- Never duplicate imports. Always reuse existing imports if they are present.
- When removing an import, do not leave behind blank lines where the import was. Ensure the surrounding code remains compact.
- Do not duplicate code. Always look for existing utility functions, helpers, or patterns in the codebase before implementing new functionality. Reuse and extend existing code whenever possible.
- Avoid using `bind()`, `call()` and `apply()` solely to control `this` or partially apply arguments; prefer arrow functions or closures to capture the necessary context, and use these methods only when required by an API or interoperability.
- Avoid using events to drive control flow between components. Instead, prefer direct method calls or service interactions to ensure clearer dependencies and easier traceability of logic. Events should be reserved for broadcasting state changes or notifications rather than orchestrating behavior across components.