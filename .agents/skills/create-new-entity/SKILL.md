---
name: create-new-entity
description: Creates a complete generic Java domain to improve efficiency as I often waste time on it.
---

# Create New Entity

Create a complete generic domain for the requested entity.
Before creating files, inspect existing domains in `src/main/java/alpha/domain`.
Use the current project code as the source of truth for structure, imports, formatting, comments, naming, and CRUD patterns.
Do not invent custom functionality.

## Structure

Create:

    src/main/java/alpha/domain/<domain>/
    ├── controller/
    │   └── <Entity>Controller.java
    ├── dao/
    │   └── <Entity>DAO.java
    ├── dto/
    │   ├── request/
    │   │   └── <Entity>RequestDTO.java
    │   └── response/
    │       └── <Entity>ResponseDTO.java
    ├── entity/
    │   └── <Entity>.java
    ├── mapper/
    │   ├── request/
    │   │   └── <Entity>RequestMapper.java
    │   └── response/
    │       └── <Entity>ResponseMapper.java
    ├── route/
    │   └── <Entity>Routing.java
    └── service/
        └── <Entity>Service.java

Only create `enums/` if explicitly required by the entity.

## Entity

Follow existing entity conventions.
Use `@Entity`, Lombok annotations, `@Table`, explicit `@Column` names, and the standard generated `Integer` ID unless another ID is explicitly requested.
Create only fields and relationships explicitly requested.
Use camelCase for Java properties and snake_case for database columns.
Preserve the project's existing entity comments, including the expected DB column layout and section separators.
Every entity must contain a nested `Fields` class with constants for every entity property.
Field constants must reference Java property names:

    public static final String FIRST_NAME = "firstName";

Never use database column names such as `"first_name"` as the constant value.

## Request DTO

Create `<Entity>RequestDTO`.

Follow existing DTO conventions:

- `@Data`
- `@JsonIgnoreProperties`
- `@JsonProperty`
- Existing `Expected JSON Input` comment style

Do not include generated IDs or server-controlled fields unless explicitly required.

## Response DTO

Create `<Entity>ResponseDTO`.

Follow existing DTO conventions and include:

- Entity ID
- Appropriate response fields
- `@JsonProperty`
- Existing `Expected JSON Output` comment style

Do not expose sensitive internal values.
For relationships, follow existing project patterns and prefer simple IDs/names instead of exposing complete JPA entities.

## Request Mapper

Create `<Entity>RequestMapper` following existing mapper conventions.

It must convert:

    <Entity>RequestDTO -> <Entity>

Only map fields present in the request DTO.
Do not perform database queries or business logic.

## Response Mapper

Create `<Entity>ResponseMapper` following existing mapper conventions.

It must convert:

    <Entity> -> <Entity>ResponseDTO

Null-check relationships before accessing nested values.
Do not add business logic.

## DAO

Create a generic DAO extending:

    EntityManagerDAO<Entity>

The DAO should contain only the constructor:

    public <Entity>DAO(EntityManager em) {
        super(em, <Entity>.class);
    }

Do NOT create custom DAO methods or JPQL queries.
Use inherited CRUD functionality.

## Service

Create a generic service extending:

    EntityManagerService<Entity>

The service should contain only the constructor:

    public <Entity>Service(EntityManager em) {
        super(new <Entity>DAO(em), <Entity>.class);
    }

Do NOT create custom service methods or business logic.

## Controller

Create `<Entity>Controller` extending:

    CRUDController<Entity>

Use the response mapper in the constructor:

    public <Entity>Controller(EntityManagerService<Entity> service) {
        super(service, <Entity>.class, <Entity>ResponseMapper::toDTO);
    }

Override `create` using the request DTO and mapper, following the existing project pattern.
Override `update` using the request DTO, mapper, path ID, and existing project pattern.
Do not override inherited generic GET or DELETE methods.
Do not create custom endpoints.
Only Override if there's a use for it. By default we only need the setup to work so we can add custom `update` and `create` if needed later on.

## Routing

Create `<Entity>Routing` extending:

    CRUDRouting<Entity>

Follow the existing routing pattern:

    public <Entity>Routing(EntityManagerFactory emf) {
        super("/<domain>", createController(emf));
    }

    private static <Entity>Controller createController(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        <Entity>Service service = new <Entity>Service(em);
        return new <Entity>Controller(service);
    }

Do not create custom routes.
If routing must be registered elsewhere, register it using the existing project pattern and make only the minimum required change.

## Rules

Always:

- Inspect at least one existing simple domain before generating files.
- Inspect the generic CRUD classes when needed.
- Follow existing package naming, imports, formatting, and comments.
- Reuse `EntityManagerDAO`, `EntityManagerService`, `CRUDController`, and `CRUDRouting`.
- Use Java camelCase and DB/JSON snake_case.
- Create only requested fields and relationships.
- Keep the implementation generic.
- Preserve the project's separator/comment style.

Never automatically create:

- Custom DAO methods
- Custom JPQL
- Custom service methods
- Custom controller methods
- Custom routes
- Search/filter methods
- Authentication logic
- Authorization logic
- Business logic
- Extra relationships
- Extra fields

Domain-specific behavior from existing domains must not be copied into the new domain.
Existing project code always takes precedence over examples in this skill.
After generation, verify package names and imports and run the normal compile/build check if available. Fix only errors caused by the newly generated domain.