# Java User REST API

This is a simple REST API project for managing users and their roles. It provides endpoints for CRUD (Create, Read, Update, Delete) operations on both users and roles.

## Technologies Used

- Java
- Spring Boot
- Maven
- RESTful API principles
- Lombok

## Endpoints

The following endpoints are available:

- `/users`: CRUD operations for managing users.
- `/roles`: CRUD operations for managing roles.

## Getting Started

To get started with the project, clone the repository from GitHub:

```
git clone https://github.com/timoteidumitru/java_user_maven_rest-api.git 

Navigate to the project directory:
 cd java_user_maven_rest-api

Then you can build and run the project using Maven:
 mvn spring-boot:run
 
The API will be available at: http://localhost:8091.
```

## Usage

#### You can use tools like cURL, Postman, or any other REST client to interact with the API. Here are some example requests:

```
Get All Users:
    GET /users
    
Get User by ID:
    GET /users/{userID}

Add User:
    POST /users
        Request Body:
            {
              "email": "example@example.com",
              "password": "password"
            }

Update User:
    PUT /users/{userID}
        Request Body:
            {
              "email": "new@example.com",
              "password": "newpassword"
            }

Delete User:
    DELETE /users/{userID}

Get All Roles:
    GET /roles

Get Role by ID:
    GET /roles/user/{roleType}

Add Role:
    POST /roles
        Request Body:
            {
              "userID": 1,
              "roleType": "admin"
            }
            
Update Role:
    PUT /roles/{roleID}
        Request Body:
            {
              "userID": 1,
              "roleType": "admin"
            }

Delete Role:
    DELETE /roles/{roleID}
Remember to replace {userID} and {roleType} with the accordingly.
```