---
name: backend-structure-refactor
description: Refactor the Java backend from a layer-based package structure to a domain-based package structure. Created by Guacamoleboy.
---

# Custom Refactor Skill created by Guacamoleboy

This skill was created by Guacamoleboy / Jonas / CPH-JL325 for the Alpha Solution Scrum Project to adjust folder structure after PO meeting in Sprint 0 reflection feedback.
The skill is added in order to save time by manually refactoring. This allows for a more efficient workflow and allows me to work on something else instead of wasting time
on a refactor that an LLM can do just as good.

## Refactor backend to domain-based structure

Refactor the Java backend from the current layer-based package structure to a domain-based structure.

### Current Structure

```text
alpha/
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── route/
├── dao/
├── mapper/
├── server/
├── service/
├── util/
└── Main.java
```

### Target Structure

Group domain-specific code under its domain, with layers underneath where relevant.

Example:

```text
alpha/
├── member/
│   ├── controller/
│   │   └── MemberController.java
│   ├── service/
│   │   └── MemberService.java
│   ├── dao/
│   │   └── MemberDao.java
│   ├── entity/
│   │   └── Member.java
│   │
│   └── And so on..
│
├─── <domain-name>/
│   ├── controller/
│   ├── service/
│   ├── dao/
│   └── entity/
│
├── config/
├── server/
├── util/
├── And so on...
├── 
└── Main.java
```

Do not create empty or unnecessary subpackages. Only create a layer under a domain if there is actually code belonging there.
For example if a domain only has one file. That file belongs under that domain in the root of that domain instead of in a folder.
Domains should only be domains if there's a real use for it. Else it belongs to the global root aspect.
Move classes to the appropriate domain based on what they actually belong to, then update all 'package' declarations and imports/references accordingly.
'src/test/..' should have its imports adjusted aswell if needed from the refactor.

#### Constraints

- **Do not modify anything under 'src/main/resources/' unless it's imports or other relevant setup.**
- Do not change application behaviour.
- Do not change API endpoints or business logic.
- Do not rename classes unless necessary.
- Do not perform unrelated refactoring.
- Preserve existing tests.
- Update tests/package declarations when necessary.
- Run the project tests/build after the refactoring and fix any issues caused by the package changes.

Before making changes, inspect the existing codebase and determine the appropriate domains rather than assuming them from the example above.