# URL Shortening Service
## Overview

This URL Shortening Service provides a simple way to convert long URLs into shortened versions, resolve them back to the original URLs, and update existing shortened URLs. The service supports custom short URL creation, rate limiting, and caching for enhanced performance and scalability.

## System Requirements

- **Java 11+**
- **Maven 3.6+** (for building the project)
- **Redis** (for caching and rate limiting functionalities)
- **MongoDB** (for persistent storage of URL and user data)

## Setup and Installation

### Clone the Repository

```bash
git clone https://github.com/your-username/url-shortening-service.git
cd url-shortening-service
```

## API Endpoints

### Create short URL

- *Method* : POST
- *Endpoint* : `/url`
- *Body* : 
```json
{
  "url": "http://ashish.bhatt/very/long/url",
  "userId": "123456",
  "expiry": 1,//in days
  "shortUrl": "optional-custom-short-url" // for creating custom url else null
}
```
- *Response* :
```json
{
  "shortUrl": "http://short.in/abc123"
}
```

### Resolve short URL

- *Method* : POST
- *Endpoint* : `/url/r/<short-url-suffix>`
- *Response* :
```agsl
Redirection to long url which is mapped to provided short url
```

### Update short URL

- *Method* : PUT
- *Endpoint* : `/url`
- *Body* :
```json
{
  "url": "http://ashish.bhatt/very/long/url",
  "userId": "123456",
  "expiry": 1,//in days
  "shortUrl": "optional-custom-short-url" // for creating custom url else null
}
```
- *Response* :
```json
{
  "shortUrl": "http://short.in/abc123"
}
```
### Create User

- **Method**: POST
- **Endpoint**: `/api/users/create`
- **Description**: Creates a new user in the system. If successful, returns the unique identifier for the newly created user.
- **Request Body**:

```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "tps": 10
  }
```

### Update User

- **Method**: POST
- **Endpoint**: `/api/users/create`
- **Description**: Creates a new user in the system. If successful, returns the unique identifier for the newly created user.
- **Request Body**:

```json
  {
    "id": "12345"
    "name": "John Doe",
    "email": "john.doe@example.com",
    "tps": 10
  }