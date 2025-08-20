
# Traineeship App

Traineeship-App is a Spring Boot web application that manages the full lifecycle of student traineeships, from application to evaluation.

## Features

### User Roles
- **Students** – update profile, apply for traineeships, and maintain logbooks.
- **Companies** – manage company profile, advertise or remove positions, track assignments in progress, and evaluate trainees.
- **Professors** – maintain profiles, supervise assigned traineeships, and submit evaluations.​
- **Traineeship Committee** – review student applications, search and match positions and supervisors using configurable strategies, finalize assignments, and pass or fail students' traineeship.​

### Security
- Username/password authentication with Spring Security, role-based access control, and custom login/logout flows.

### Search Strategies
- Strategy pattern for matching students with positions and supervisors based on location, interests, and workload.​

## Tech Stack
- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL

## Getting Started

### Prerequisites
- JDK 21+
- Maven or the included Maven Wrapper (`mvnw`)
- MySQL database

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/ThePhantom2307/Traineeship-App.git
   cd Traineeship-App
   ```
   
2.  Update database credentials in  `src/main/resources/application.properties`  to match your local MySQL instance.
    
3.  (Optional) Import the provided SQL scripts (`myy803_traineeship.sql`,  `myy803_traineeship-static.sql`) to pre-populate the database.
