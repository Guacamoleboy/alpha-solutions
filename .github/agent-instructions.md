# Alpha Solution Agent Instructions

## Project Overview

Alpha Solution is about using Scrum as a development team and work in Sprints throughout the course. We will progress using Scrum techniques and try to integrate AI into our Scum basis.

### Root Folders

- `.github/`: CI / CD, Workflows, Agents and other important tasks related to Github and Agents
- `docs/`: Files and important documents for documentation reasons
- `frontend/`: Portfolio website for AIDA
- `backend/`: Java REST App for Rubric and client learning purposes

### Core Architecture (`frontend/` folder)

- `frontend/node_modules/` - Installed npm dependencies. Do not modify files in this directory manually.
- `frontend/public/` - Static assets that are served directly by the frontend.
- `frontend/src/` - Main frontend source code.
- `frontend/src/app/` - Pages, layout, access, roles and entry point for the portfolio
- `frontend/src/features/` - Components that are page specific only
- `frontend/src/features/any-page/` - Components are page based.
- `frontend/src/shared/` - Used for shared context across the portfolio. Globals, types, styling and misc.

### Java Backend Architecture (`backend/` folder)

- `backend/src/` - Main application folder. Like any other java application. Maven.
- `backend/.idea/` - Don't adjust manually 
- `backend/.mvn/` - Don't adjust manually 
- `backend/docs/` - Folder for documentation, learning purpose and general personal knowledge
- `backend/target/` - Don't adjust manually 
- `backend/src/main/resources` - Java specific resources includes things such as rubric, prompts, student reports and env files. Don't ever leak the env files.
- `backend/src/main/java/alpha/` - The Java application ran through Main.java

### Finding Related Code

1. **Semantic search first**: Use file search for general concepts
2. **Grep for exact strings**: Use grep for error messages or specific function names
3. **Follow imports**: Check what files import the problematic module
4. **Check test files**: Often reveal usage patterns and expected behavior

## Validating TypeScript changes

- `npm run typecheck-client` for the main sources under `frontend/`
- Do not run or ask the user to run `npm run build` unless the user explicitly requests a build.

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

- Do not export `types` or `functions` unless you need to share it across multiple components
- Do not introduce new `types` or `values` to the global namespace

### Comments

- Do not add comments unless it's super important. Comments in codebase should be written by the developer and not the Agent.

### Code Quality

- All .tsx, .js, .ts, .jsx files must include a pathing header
- Prefer `async` and `await` over `Promise` and `then` calls
- Look for existing test patterns before creating new structures
- If you create any temporary new files, scripts, or helper files for iteration, clean up these files by removing them at the end of the task
- Never duplicate imports. Always reuse existing imports if they are present.
- When removing an import, do not leave behind blank lines where the import was. Ensure the surrounding code remains compact.
- Do not use `any` or `unknown` as the type for variables, parameters, or return values unless absolutely necessary. If they need type annotations, they should have proper types or interfaces defined.
- Do not duplicate code. Always look for existing utility functions, helpers, or patterns in the codebase before implementing new functionality. Reuse and extend existing code whenever possible.
- Avoid using `bind()`, `call()` and `apply()` solely to control `this` or partially apply arguments; prefer arrow functions or closures to capture the necessary context, and use these methods only when required by an API or interoperability.
- Avoid using events to drive control flow between components. Instead, prefer direct method calls or service interactions to ensure clearer dependencies and easier traceability of logic. Events should be reserved for broadcasting state changes or notifications rather than orchestrating behavior across components.