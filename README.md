# motoShop - a web application

**Topic:** A solo pet project about an ERP software for a motorcycle shop, designed to be as a modern Single-page web application.
The project is currently under development, with a functional Java back-end server, to which a React front-end user-interface
is connected. Java back-end is using JDBC at this point, a low level persistence technology for database manipulation,
and communicating via REST APIs. During the following iterations, JDBC will be changed to a JPA implementation, to experience
such a swift in the underlying technology.

**The aim of the project:** During the development, I would like to learn the proper usage of JDBC, a low level interface for
relational database management, compared to an ORM. It is also a good opportunity to practice building REST APIs with Java Spring,
practice the build of UI elements with ReactJS, use pure SQL syntax, and to properly design a relational database in accordance
with object-oriented Java code, to avoid object-relational impedance mismatch. During the next iteration, I would like to gather
further experience by switching JBDC to ORM, while proper unit test coverage should help a clear alteration.

<br>

## What I have used so far during development:
- Back-end:
    - Java SE JDK 17
    - Java Database Connectivity (JDBC)
    - PostgreSQL
    - Spring framework
    - Rest API
    - Maven
    - JUnit 5
    - DAO design pattern


- Front-end:
    - JavaScript
    - ReactJS
    - React-bootstrap
    - Formik
    - CSS
    - Asynchronous programming

<br>

## How to use:
Establish project:
1. Fork my repository (do not clone it!)
2. Create Postgre SQL **database** and **login role** on your machine, with following parameters:
    - Database name: motoShop
    - User name: ibardos_GitHub_demoProject
    - Password: Asd123

Back-end testing:
1. Copy my HTTP request collection from <a href="https://api.postman.com/collections/22591207-510d8b2d-6452-4c05-8071-69a0f6f49f3c?access_key=PMAT-01GP9E5JR8QAT02N5377EF28MQ">here</a>
2. Import the collection as raw text into <a href="https://www.postman.com/downloads/">Postman</a>
3. Open the project in your IDE
4. Start the back-end server in your IDE
5. Initialise database by running script.sql and data.sql respectively
6. Test the API endpoints with the predefined HTTP requests in Postman
7. Or you can simply run the available unit tests against the API endpoints, as it will initialise database automatically for you
8. Be happy!

Front-end:
1. Start the servers (preferably with a predefined Compound in your IDE)
2. Navigate through the menu points
3. Try to create/read/update/delete data at any page
4. Be happy!

<br>

## Hints:
- You can create a Postgre SQL database in your terminal, but I recommend to use <a href="https://www.pgadmin.org/download/">"pgAdmin 4"</a> if you prefer to use a GUI.
- You can download Postman from the link above, if you haven't already.
- Create a compound in your IDE to be able to run back-end and front-end servers simultaneously with a push of a button.
  - Don't forget to also initialise database with server starts (run script.sql and data.sql)
  - Tip: Database initialisation scripts can be added to Compound of back-end and front-end start
- If you don't understand something, Google it, or feel free to contact me.
