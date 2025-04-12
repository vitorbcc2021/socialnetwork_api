# Social Network API Documentation
This is my RESTFul API developed in Java Spring Boot following some architectural concepts of **Leonard Richardson**.

## ⚠️ Important!
  I can't find a way to host the API for free, so i have to keep the entire Social Network project running locally.  
  It can change soon 😉

## 🧑‍💻 Technical Specifications

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
  - `spring-boot-starter-security`

### Database
You will need PostgreSQL 17 or higher to run the application. Manually, create an database called: *socialnetwork*.  
On the file **application.properties**, you will need to do some changes to work on your PostgreSQL:
- **Credentials**:
  ```properties
  spring.datasource.username=your_psql_user
  spring.datasource.password=your_psql_password
  ```

---

## 🖥️ API Endpoints

### User Routes

| Method | Endpoint                     | Description              |
|--------|------------------------------|--------------------------|
| POST   | `/users/`                    | Create user              |
| POST   | `/users/login`              | Authenticate user        |
| GET    | `/users/{id}`               | Get user by ID           |
| GET    | `/users/`                   | List all users           |
| PUT    | `/users/{id}`               | Update user              |
| DELETE | `/users/{id}`               | Delete user              |
| POST   | `/users/{userId}/follow`    | Follow another user      |
| POST   | `/users/{userId}/unfollow`  | Unfollow a user          |

---

## 🧪 Request/Response Examples

### ➕ Create User (`POST /users/`)

**Request**:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepassword",
  "profilePicture": "https://example.com/img.jpg",
  "banner": "https://example.com/banner.jpg"
}
```

#### Response (201 Created)
```json
{
  "id": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
  "name": "John Doe",
  "email": "john@example.com",
  "profilePicture": "https://example.com/img.jpg",
  "banner": "https://example.com/banner.jpg",
  "_links": {
    "self": {
      "href": "http://localhost:8080/users/a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8"
    },
    "all-users": {
      "href": "http://localhost:8080/users"
    }
  }
}

```

### Get User by ID (GET `/user/{id}`)
**Response (200 OK)**
```json
{
  "id": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
  "name": "John Doe",
  "email": "john@example.com",
  "profilePicture": "https://example.com/img.jpg",
  "banner": "https://example.com/banner.jpg",
  "_links": {
    "self": {
      "href": "http://localhost:8080/users/a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8"
    },
    "all-users": {
      "href": "http://localhost:8080/users"
    }
  }
}

```

### Create Post (POST `/post/`)
**Request**:
```json
{
  "authorId": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
  "imgPath": "url/para/imagem.jpg",
  "likes": []
}

```

#### Response (201 Created)
```json
{
  "id": "d6f8c8a0-8b7d-11ed-a1eb-0242ac120002",
  "author": {
    "id": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
    "name": "John Doe",
    "email": "john@example.com",
    "profilePicture": "url/para/imagem.jpg"
  },
  "imgPath": "url/para/imagem.jpg",
  "likes": [],
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

### Get Post by ID (GET `/post/{id}`)
**Response (200 OK)**
```json
{
  "id": "d6f8c8a0-8b7d-11ed-a1eb-0242ac120002",
  "author": {
    "id": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
    "name": "John Doe",
    "email": "john@example.com",
    "profilePicture": "url/para/imagem.jpg"
  },
  "imgPath": "url/para/imagem.jpg",
  "likes": [],
  "_links": {
    "Post List": {
      "href": "http://localhost:8080/post/"
    }
  }
}

```

## HATEOAS Implementation
All resources include navigable links. Example:
```json
{
  "id": "d6f8c8a0-8b7d-11ed-a1eb-0242ac120002",
  "author": {
    "id": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8",
    "name": "John Doe",
    "email": "john@example.com",
    "profilePicture": "url/para/imagem.jpg"
  },
  "imgPath": "url/para/imagem.jpg",
  "likes": [],
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


