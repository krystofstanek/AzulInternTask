# Bookstore Inventory Management System

## Overview
The Bookstore Inventory Management System is a RESTful web application built with Spring Boot. It allows bookstore owners to efficiently manage their inventory through CRUD operations and search functionality. The system provides endpoints for adding, updating, and removing books, as well as for searching books by title, author, genre, price range, and ISBN. It also includes basic authentication and role-based access control using Spring Security.

## Features
- **Book CRUD Operations:**
    - **Add Books:** Create new book entries (Admin only).
    - **Update Books:** Modify existing book details (Admin only).
    - **Remove Books:** Delete books or decrement their quantity (Admin only).
- **Search Functionality:**
    - Search by **title**, **author**, or **genre** (paginated).
    - Search by **price range** (paginated).
    - Search by **ISBN** (not paginated).
 
- **Authentication and Authorization:**
    - **Basic HTTP authentication** using Spring Security.
    - Differentiation between **admin** users (full CRUD permissions) and **regular** users (view-only).
- **Database Integration:**
    - Uses **Spring Data JPA** for ORM.
    - Custom queries and repository methods to support various search criteria.
- **Testing:**
    - Unit tests for model validations and business logic.
    - Repository tests to verify database operations.
    - Manual API testing using Postman.

## Technologies Used
- **Java** 
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **JUnit 5** for testing
- **PostgreSQL** 

## Project Structure
- **`com.krystofstanek.Azul.Intern.Task.api.controller`**  
  Contains the REST controllers (e.g., `BookstoreController`) that handle API requests.
- **`com.krystofstanek.Azul.Intern.Task.api.model`**  
  Defines the domain models such as `AbstractBook`, `SimpleBook`, and the `Genre` enum.
- **`com.krystofstanek.Azul.Intern.Task.api.repository`**  
  Contains the repository interfaces for database operations using Spring Data JPA.
- **`com.krystofstanek.Azul.Intern.Task.service`**  
  Implements the business logic in services like `BookService`.
- **`com.krystofstanek.Azul.Intern.Task.config`**  
  Contains configuration classes, including `SecurityConfig` for Spring Security.
- **`com.krystofstanek.Azul.Intern.Task.exceptions`**  
  Custom exceptions such as `BookNotFoundException`.
- **Tests:**  
  Unit and repository tests (e.g., `AbstractBookTest`, `BookRepositoryTest`, and a context load test).

## Setup and Running the Application

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/krystofstanek/AzulInternTask
   cd AzulInternTask
   ```

2. **Build the Application:**
   For Maven:
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   Start the application using:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API:**
   The application runs on [http://localhost:8080](http://localhost:8080).

    - **Admin Credentials:**
        - **Username:** `admin`
        - **Password:** `password`

    - **Endpoints Overview:**
        - **POST `/books`**  
          *Description:* Add a new book (requires admin role).  
          *Payload Example:*
          ```json
          {
            "isbn": "9783161484100",
            "title": "Effective Java",
            "author": "Joshua Bloch",
            "genre": "EDUCATIONAL",
            "price": 45.99,
            "quantity": 5
          }
          ```
        - **DELETE `/books`**  
          *Description:* Remove a specified quantity of a book (requires admin role).  
          *Parameters:* `isbn`, `quantity`
        - **PATCH `/books`**  
          *Description:* Update an existing book (requires admin role).  
          *Parameters:* `isbn` (query parameter), with updated book details in the body.
        - **GET `/books/{isbn}`**  
          *Description:* Retrieve a book by ISBN.
        - **GET `/books/genre`**  
          *Description:* Retrieve books by genre with pagination.
        - **GET `/books/author`**  
          *Description:* Retrieve books by author with pagination.
        - **GET `/books/title`**  
          *Description:* Retrieve books by title with pagination.
        - **GET `/books/price`**  
          *Description:* Retrieve books within a specified price range with pagination.

## Testing

### Automated Tests
The project includes a suite of tests to verify its functionality:

- **Model Tests:**  
  `AbstractBookTest` checks validations for attributes like ISBN, title, author, genre, price, and quantity, along with the update quantity logic.

- **Repository Tests:**  
  `BookRepositoryTest` verifies repository methods for finding books by various attributes and ensures correct database operations.

- **Context Load Test:**  
  `AzulInternTaskApplicationTests` ensures that the Spring Boot application context loads correctly.

To run the tests:
```bash
mvn test
```

### Manual Testing
Postman was used for manual testing to simulate HTTP requests and verify API behavior. You can use Postman to perform the following actions:
- Add a new book.
- Update and delete a book.
- Retrieve books by genre, author, title, or price range.


## Conclusion
This project provides a strong starting point for a Bookstore Inventory Management System with a clean separation of concerns, basic authorization, and testing. It lays the groundwork for further enhancements and scalability.
