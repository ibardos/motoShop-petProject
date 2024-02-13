# motoShop - a modern ERP web application

**Topic:** A solo "pet project" about an ERP software for a motorcycle retail shop, designed to be a modern single-page
web application.

The project is currently under development, with a functional Java back-end server, to which a ReactJS front-end
user-interface is connected. The back-end and the front-end are communicating via REST APIs. For persistent data storage,
a PostgreSQL relational database was established.

**The aim of the project:** I started this project after finishing my full-stack developer studies at Codecool, to further
extend my practical knowledge related to software development. The main focus of the project was to gather as much
experience as possible with modern software development techniques, frequently used design patterns, frameworks, persistence
technologies, and best practices, all related to a modern Java based web application.

During development, I wanted to imitate real-life scenarios to the fullest extent, hence the choice of the "two-tier"
server architecture (Java back-end + ReactJS front-end), consequent use of Git branching, and the use of modern tools and
practices like building up a CI/CD pipeline, containerization, and Test-Driven-Development. I also wanted to face some
specific challenges, that can occur in real life, so I intentionally planned the project to have space for modernization.

**Modernizing the persistence layer:** Modernization is a common task in software development. Persistence technologies
have evolved significantly over the past decades and as a result, an Object-relational mapper (ORM) nowadays can offer much
more versatility than low-level persistence technologies, such as pure JDBC.

To gain knowledge with both technologies, and to experience such a switch in the underlying persistence layer, this project
was designed to use an older persistence technology (JDBC) at first, which then was successfully changed to a modern
JPA implementation (Hibernate - ORM), at a later iteration.

This was a good opportunity to learn the proper usage of JDBC, a low-level persistence technology for relational database
management with pure SQL syntax, to practice building REST APIs with Java Spring, and to learn how to properly design a
relational database in accordance with object-oriented Java code, to avoid object-relational impedance mismatch.

The use of ORMs are much more frequent in modern Java applications, therefore now the application is using Hibernate,
a modern Object-relational mapper for Java, ensuring proper database operations.

As it would happen in a real-life scenario, the change of the persistence layer was supported by proper test coverage to
ensure a smooth and error free transition. The API endpoints at the back-end server were covered with End-to-End tests
capable to verify the correct operation and their integration as well to other software components.

To being notified about further developments on the project, please consider "watch" the repository on GitHub.

## What I have used during development:
#### Back-end:
- Java SE JDK 17
- Java SE JDK 21
- Java Database Connectivity (JDBC)
- Structured Query Language (SQL)
- Java Persistence API (JPA)
- Object-Relational Mapping (ORM) - Hibernate
- PostgreSQL relational-database management system
- H2 database engine (used during testing in CI environment)
- Spring Boot
- Spring Security
- Rest API
- Maven
- JUnit 5
- Stateless session handling with JWT
- DAO design pattern
- Controller-Service-Repository (CSR) pattern

#### Front-end:
- JavaScript
- ReactJS
- HTML5
- Formik
- React-bootstrap
- CSS
- JavaScript Fetch API
- Asynchronous programming

#### In general:
- Git version control system
- GitHub Actions - CI workflow
- JetBrains IntelliJ IDEA
- Postman

## Steps of development:
1. Design relational database
   - Tables
   - Properties
   - Relations
2. Initialize database
   - Create DDL script - schema.sql
   - Create DML script - data.sql
3. Establish Java back-end server with JDBC
   - Implement database management
   - Create Model classes
   - Implement DAO pattern for CRUD operations on database with JDBC
     - Create DAO interfaces
     - Create DAO implementations
   - Create Controller classes with REST APIs
   - Implement End-to-End tests for proper API endpoint verification
4. Establish ReactJS front-end server
   - Design UI structure
     - Create initial UI pages
     - Implement React routing
   - Create React Components to populate pages
   - Implement state management
   - Utilize JS Fetch API to communicate with back-end server
   - Manual testing of the User Interface
5. Change the persistence technology from JDBC to ORM
   - Change project dependencies (JDBC -> Hibernate)
   - Change database connection according to Spring Data JPA
   - Refactor Model classes into "entities" to work with Hibernate
     - Utilize Spring Data annotations
     - Utilize Lombok to eliminate boilerplate code required by Hibernate
   - Implement CSR pattern for CRUD operations on database with Spring Data JPA
     - Create Repository interfaces
     - Create Service classes
   - Refactor Controllers to work with the newly created Service layer
   - Test API endpoints with End-to-End tests to check the transition was successful
6. Establish a Continuous Integration (CI) pipeline - GitHub Actions
    - Create YAML file to establish pipeline with automated build and tests before code integration
      - Implement workflow for back-end (Java 21)
      - Implement workflow for front-end (node 16, 18, 21)
    - Reshape existing Java End-to-End tests to run in CI environment
      - Establish a mock database to be used during testing in CI environment
        - Update project dependencies (introduce H2)
        - Utilize an embedded relational-database with PostgreSQL dialect, capable of running in-memory - H2
      - Refactor existing API tests to use the H2 database
      - Implement "automatic back-end server start" in CI environment to test API endpoint integrations
    - Configure GitHub "Branch protection rules" for *master* branch
      - Require "pull request" from different branches to merge code
      - Require successful CI pipeline builds and tests to merge code
7. Implement authentication and authorization functionalities by utilizing Spring Security at the back-end server
    - Create ApplicationUser entity
    - Create Roles and Permissions
    - Implement CustomUserDetails and corresponding Service class to customize the authentication process
    - Implement stateless back-end server session handling with JWT
    - Create REST controller for authentication and registration
    - Configure security levels for API endpoints
      - Role and Permission based authorization
    - Refresh DDL & DML scripts to update database
    - Refresh End-to-End tests:
      - Create API tests against newly created AuthenticationController
      - Update existing API tests to work with secured endpoints from now on

### Future development plans:
8. Implement login and registration features at the front-end side
   - Create new React components to handle login/registration through UI
   - Implement conditional UI element rendering according to user roles
9. Containerize application - Docker
   - Create Dockerfile
   - Create Shell scripts to build and run Dockerfile
10. Deploy the application to a public server
    - Utilize previously implemented GitHub Actions CI pipeline for deployment
      - Update CI pipeline into CI/CD to have Continuous Deployment as well
    - Utilize previously created Dockerfile for containerization
    - Add job to YAML file to handle automatic deployment in a containerized environment

## How to use this repository:
#### Establish project:
1. Fork my repository (do not clone it!)
2. Create Postgre SQL **database** and **login role** on your machine, with following parameters:
    - Database name: motoShop
    - User name: ibardos_GitHub_demoProject
    - Password: Asd123
3. Download node dependencies for ReactJS
   1. Open a terminal here: ```~/src/main/ui```
   2. Run command: ```npm install```
4. Create a run configuration in your IDE for the back-end server - Java 21
5. Create a run configuration in your IDE for the front-end server - npm

<br>

#### Back-end testing:

With Postman:
1. You can find a collection of HTTP requests in this folder of the repository: ```~/src/test/resources/motoShop.postman_collection.json```
2. Import the collection as raw text into <a href="https://www.postman.com/downloads/" target="_blank">Postman</a>
3. Open the project in your IDE (I've used <a href="https://www.jetbrains.com/idea/download/?section=mac" target="_blank">IntelliJ IDEA Ultimate</a>)
4. Start the back-end server in your IDE
   - Database tables will be created and initialized with data automatically
5. Test the API endpoints with the predefined HTTP requests in Postman (edge cases are also covered)
   - Remember to retrieve the proper (see hints) JWT token to be able to call service APIs

With my set of End-to-End tests: 
1. Run the tests I've created for API endpoint testing, located here: ```~/src/test/java/com/ibardos/motoShop/endToEndTests/apiTests```
   - The defined tests have automatic database setup/cleanup code as well in a mock database specifically for testing purposes,
so you don't have to bother with that

**IMPORTANT:** I managed to "store" the Java back-end application with both persistence technologies used during the project,
JDBC and ORM as well. You can perform the above-mentioned End-to-End tests against the back-end APIs working with JDBC, or
even with ORM, by checking out one of the available Git branches in the ```milestone``` git folder, to choose between the
two distinct "versions" of the Java back-end code. Although they're built different "under the hood", you should notice, that
they perform exactly the same way. Don't forget to start the Java back-end server by hand before start testing in these
branches, as opposed to the "latest" version of the application, these older variants do not have automatic server start!

<br>

#### Front-end:
FRONT-END cannot be used currently. Security related functionalities are under development!
1. Start the back-end and front-end servers simultaneously (preferably with a predefined Compound in your IDE)
2. Navigate through the menu points in your web browser
3. Try to create/read/update/delete data at any page

**IMPORTANT:** The front-end can be tested as well with both persistence technologies used during the project on the
back-end side, by checking out one of the available Git branches in the ```milestone``` git folder. After successful checking,
start the front-end server and use the application with its Graphical User Interface (GUI), just as I described in the
section above. You should notice by using the GUI, that everything will perform just exactly the same way with JDBC, and
with ORM as well.

<br>

## Hints:
- You can create a Postgre SQL database in your terminal, but I recommend to use <a href="https://www.pgadmin.org/download/" target="_blank">"pgAdmin 4"</a> if you prefer to use a GUI.
- You can download Postman from the link above, if you haven't already. It is a really helpful tool during API development.
- Create a compound in your IDE to be able to run back-end and front-end servers simultaneously with a push of a button.
  - You don't need to bother with database initialisation at any point, as I managed to do that programmatically.
- If you don't understand something, Google it, ask ChatGPT about it, or feel free to contact me.
- Security configuration:
  - User role: read permission
  - Sales role: create, read, update permissions
  - Admin role: create, read, update, delete permissions
