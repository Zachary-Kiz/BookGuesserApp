# BookGuesser Backend

Link to App: [BookGuesser.app](https://bookguesser.app/today)

Link to Frontend: [BookGuesser-Frontend](https://github.com/Zachary-Kiz/BookGuesser-Frontend)

Link to Daily Puzzle Upload Pipeline: [BookGuesser-Pipeline](https://github.com/Zachary-Kiz/BookGuesser-Scraper)

## Overview

The BookGuesser backend is a Spring Boot REST API that powers the daily puzzle game experience. It manages user authentication, puzzle generation, game logic, user statistics, friendships, and communication with the PostgreSQL database.

The backend provides the services required by the BookGuesser frontend, handling secure user sessions, validating guesses, storing player progress, and retrieving daily puzzle data.

## Features

- User registration and login
- JWT-based authentication with secure cookie storage
- Daily puzzle retrieval and game state management
- Guess validation and score tracking
- User statistics and gameplay history
- Friend management and score comparison
- PostgreSQL data persistence
- Automated book cover processing pipeline integration

## Technical Overview

The backend was developed using:

- **Java** - Backend application development
- **Spring Boot** - REST API framework and application architecture
- **Spring Security** - Authentication and authorization
- **Hibernate/JPA** - Database interaction and object-relational mapping
- **PostgreSQL** - Relational database for application data
- **Maven** - Dependency management and build automation
