# Contribution guide for Alpha Solution Scrum Project

This document is made to establish and define code standards for the project. All contributors are expected to follow these when modifying or contributing to the project.

## 1. Naming and Formatting

### General

- English unless it's internal logs for PO.
- Use clear and descriptive names for variables, classes and other features.
- Avoid abbreviations.

### Java

- Classes use PascalCase
- Methods and variables use camelCase
- Constants use UPPER_SNAKE_CASE

### JavaScript

- Variables and functions use camelCase
- React components use PascalCase
- Constants use UPPER_SNAKE_CASE

### Markdown

- Use parent / child node layout
- Be clear and professional

## 2. Folder Structure

### Backend

```text
backend/
└── src/ 
    |
    └── main/
        |
        ├── resources/
        |       |
        |       └── http/
        └── java/
            |
            └── alpha/
                |
                ├── config/
                ├── controller/
                ├── dto/
                ├── dao/
                ├── entity/
                ├── exception/
                ├── route/
                ├── server/
                ├── service/
                ├── util/
                └── Main.java
```

### Frontend

```text
frontend/
└── src/ 
    |
    ├── app/
    |   ├── pages/
    |   ├── routes/
    |   ├── layout/
    |   ├── App.jsx
    |   └── main.jsx
    |
    ├── feature/   
    |       └── component/
    |               ├── Component.jsx
    |               ├── Component.hooks.js (If needed)
    |               └── Component.module.css
    └── shared/
            ├── style/
            |   └── globals.css
            ├── data/
            └── components/
```

## 3. Branch rules

- Do not work in main or development
- Create your own branch from development

## 4. Commit Messages

Should be clear, professional, short and no long essays. It's simply to establish what was done. The overall depth will be in the Pull Request.

## 5. Pull Requests

- Should have a title
- Should follow the pull_request_template provided during pull request
- Should allow the other person to approve the pull request prior to codebase entry (Tine ?)
- Pass all CI checks

## 6. DTO

In order to comply with EU law, GDPR and internal structures our DTO logic is sacred. Meaning the following should always be the rule of thought.

- Do not expose database IDs or other critial information in the DTOs.
- Request and response DTOs should be seperated.
- DTO names should follow the entity names.

Example:

Member (Entity)
MemberResponse
MemberRequest

## 7. Validation

Validation happens before service or logic happens internally. Meaning our program should respond once a person without access tries to enter.
Validation errors should be consistent and professional.

## 8. Exception handle

We are using Java Exception Handle and uses that to showcase feedback to the customer via a notification UI element.