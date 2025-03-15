# Social Network API Documentation
This is my RESTFul API developed in Java Spring Boot following some architectural concepts of **Leonard Richardson**.

## Technical Specifications

### Dependencies
- **Spring Boot**: 3.4.3
- **Java Version**: 23
- **Key Libraries**:
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-hateoas`
  - `spring-boot-starter-web`
  - `spring-boot-starter-validation`
  - `postgresql`
  - `spring-boot-starter-test`

### Database
- **Type**: PostgreSQL
- **Name**: `socialnetwork`
- **Credentials**:
  - Username: `postgres`
  - Password: `admin`

---

## API Endpoints

### User Routes

| Method | Endpoint       | Description          |
|--------|----------------|----------------------|
| POST   | `/user/`       | Create user          |
| GET    | `/user/{id}`   | Get user by ID       |
| GET    | `/user/`       | List all users       |
| PUT    | `/user/{id}`   | Update user          |
| DELETE | `/user/{id}`   | Delete user          |

### Post Routes

| Method | Endpoint       | Description          |
|--------|----------------|----------------------|
| POST   | `/post/`       | Create post          |
| GET    | `/post/{id}`   | Get post by ID       |
| GET    | `/post/`       | List all posts       |
| PUT    | `/post/{id}`   | Update post          |
| DELETE | `/post/{id}`   | Delete post          |

---

## Request/Response Examples

### Create User (POST `/user/`)
**Request**:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "profilePicture": "url/to/image.jpg"
}
```

#### Response (201 Created)
``` json
{
  "userID": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
  "_links": {
    "User List": {
      "href": "http://localhost:8080/user/"
    }
  }
}
```

## HATEOAS Implementation
All resources include navigable links. Example:
```json
{
  "postID": "d6f8c8a0-8b7d-11ed-a1eb-0242ac120002",
  "_links": {
    "self": {
      "href": "http://localhost:8080/post/d6f8c8a0-8b7d-11ed-a1eb-0242ac120002"
    },
    "Post List": {
      "href": "http://localhost:8080/post/"
    }
  }
}
```

## Database Configuration (application.properties)
```json
spring.datasource.url=jdbc:postgresql://localhost:5432/socialnetwork
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

## Richardson Maturity Level: 3 (Hypermedia-driven API)
Indicates the use of HATEOAS (Hypermedia as the Engine of Application State), where API responses include navigable hyperlinks.  
This allows clients to discover and interact with resources dynamically, without relying on prior knowledge of the endpoints. The API becomes self-describing and evolvable, following the most rigorous RESTful principles.