# Custom Architecture Checkup Skill created by Guacamoleboy

Once a domain-layer architecture has been added this skill provides LLM checks in case classes implemented later on doesn't follow the structure.
Allows for ease-of-use and fast checks in order to comply with PO meeting in Sprint 0.

## Custom Architecture Check Skill

Check the Java backend against the existing domain-based package structure.

### Expected Structure

Domain-specific code should follow:

```text
alpha/
├── <domain>/
│   ├── controller/
│   ├── service/
│   ├── dao/
│   ├── entity/
│   ├── dto/
│   ├── mapper/
│   └── ...
│
├── config/
├── server/
├── util/
└── Main.java
```

Only use subpackages when they are actually relevant. If a domain only has one file, keep it directly under the domain rather than creating an unnecessary subpackage.

### Instructions

Inspect the current code and determine whether new or existing code follows the domain-based architecture.

If something is incorrectly placed:

- Move it to the appropriate domain/package.
- Update 'package' declarations and imports/references.
- Adjust test imports/package declarations if necessary.
- Preserve existing behaviour and business logic.

Before moving anything. Let the developer know what the issues are and if they want to implement the things you have found.
If everything already follows the architecture, make no changes.

### Constraints

- Do not perform unrelated refactoring.
- Do not change API endpoints or application behaviour.
- Do not rename classes unless necessary for the refactor.
- Do not modify 'src/main/resources/' unless a package/import-related configuration change is strictly required.
- Preserve and run existing tests/build after any changes.