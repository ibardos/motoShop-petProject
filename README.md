# motoShop - a web application

**Topic:** A solo pet project about an ERP software for a motorcycle shop, designed to be as a modern one-page web application.
The project is currently under development, with an already functional Java back-end server, to which a React front-end will be
connected. Java back-end is using JDBC, a low level persistence technology for database manipulation, and communicating via REST APIs.

**The aim of the project:** During the development, I would like to learn the proper usage of JDBC, a low level interface for
relational databse management, compared to an ORM. It is also a good opportunity to practice building REST APIs with Java Spring,
practice React, use SQL, and to properly design a relational database in accordance with object-oriented Java code,
to avoid object-relational impedance mismatch. 

<br>

## What I have used so far during development:
- Java
- Java Database Connectivity (JDBC)
- PostgreSQL
- Spring framework
- Rest API
- Maven
- DAO design pattern

<br>

## How to use:
Back-end:
1. Fork my repository (do not clone it!)
2. Create Postgre SQL **database** and **login role** on your machine, with following parameters:
    - Database name: motoShop
    - User name: ibardos_GitHub_demoProject
    - Password: Asd123
3. Copy my HTTP request collection from <a href="https://api.postman.com/collections/22591207-510d8b2d-6452-4c05-8071-69a0f6f49f3c?access_key=PMAT-01GP9E5JR8QAT02N5377EF28MQ">here</a>
4. Import the collection as raw text into <a href="https://www.postman.com/downloads/">Postman</a>
5. Open the project in your IDE
6. Start the back-end server in your IDE
7. Test the API endpoints with the predefined HTTP requests in Postman
8. Be happy!

<br>

## Hints:
- You can create a Postgre SQL database in your terminal, but I recommend to use <a href="https://www.pgadmin.org/download/">"pgAdmin 4"</a> if you prefer to use a GUI.
- You can download Postman from the link above, if you haven't already.
- If you don't understand something, Google it, or feel free to contact me.
