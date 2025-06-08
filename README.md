# motoShop - a modern ERP web application

**Topic:** A solo "pet project" about an ERP software for a motorcycle retail shop, designed to be a modern single-page
web application.

The application is now fully deployed and available online, providing a seamless experience with a modern Java back-end
server and ReactJS front-end user interface communicating via REST APIs. Persistent data storage is managed with a 
PostgreSQL relational database, secured with Spring Security and role-based authorization. Session handling is stateless
using JWT web-tokens.

**Live Demo:** <a href="https://motoshop-petproject.netlify.app" target="_blank">motoShop - ERP</a>

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
8. Implement login and registration features at the front-end side
    - Create new React components to handle login/registration through UI
    - Implement conditional UI element rendering (buttons, navbar) according to user roles and permissions
9. Deploy application to public hosting
    - Containerize the application using Docker
    - Deploy back-end and front-end components to platforms such as Render, Netlify, Supabase
    - Configure environment variables and database connections for production
    - Enable continuous deployment through GitHub Actions CI/CD pipeline

### Future development plans:
10. Containerize application - Docker (local deployment support coming soon)
    - Create Dockerfile
    - Create Shell scripts to build and run Dockerfile
11. Implement further functionalities
    - Banking
    - Customers
    - Orders
    - User management
    - Account management

## Note on local setup:
Local deployment instructions via manual IDE configurations have been removed as the application is now fully deployed online.
Support for local deployment using Docker will be provided soon. Please stay tuned for updates.

## Hints:
- You can create a Postgre SQL database in your terminal, but I recommend to use <a href="https://www.pgadmin.org/download/" target="_blank">"pgAdmin 4"</a> if you prefer to use a GUI.
- You can download Postman from the link above, if you haven't already. It is a really helpful tool during API development.
- If you don't understand something, Google it, ask ChatGPT about it, or feel free to contact me.

- Security configuration back-end:
  - User role: read permission
  - Sales role: create, read, update permissions
  - Admin role: create, read, update, delete permissions

- Security configuration front-end:
  - username: user, password: user
  - username: sales, password: sales
  - username: admin, password: admin
