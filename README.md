# Alpha Solution Scrum Project

Project created by Khuong & Jonas for the 4th Term of AP Computer Science Denmark.

[![Visit](https://img.shields.io/badge/Visit-ffffff?style=for-the-badge&color=f99e00)](http://alpha.guacamoleboy.dk)

---

> [!NOTE]  
> All files in the **[main]** branch are final and deployed

---

## Links

REST API: N/A
Website: N/A
Github Projects: N/A
Docs: N/A

---

## Short presentation

Our project is a Pickelball planner tool for the Product Owner. The Product Owner in our case is the owner of the facility. The CEO.

Why?
A pickelball court is limited in staff needed in order to operate. Most staff needed is service personel.

We are going to include Staff Planning as a seperate tool for the Product Owner in order to fully comply with the task provided by Klaus during class.
This should showcase the Product Owners resources at any given time of the day and provide a clear overview along with (hopefully) warnings if the team capacity is at limit or close to it.

---

## Folder Structure

This section is to showcase our folder structure. We are using a shared / feature architecture for out frontend application and a normal CRUD REST API Setup for our Java backend.

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

---

<div align="center">
    <sub>Alpha Solution Scrum Project - 2026</sub>
</div>