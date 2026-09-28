# TinyURL

A URL shortening application built with **Spring Boot** and **Next.js**. The application converts long URLs into short, shareable links and redirects users to the original URL.

The backend is containerized using **Docker** and deployed on **Render**, while the frontend is deployed on **Vercel**. PostgreSQL is used as the production database through Supabase.

## Features

* Generate short URLs from long URLs
* Redirect short URLs to the original URL
* Unique short-code generation
* REST API for URL shortening
* PostgreSQL database integration
* Dockerized Spring Boot backend
* Next.js frontend
* Production deployment
* CORS configuration for frontend-backend communication
* Postman API collection for testing

## Tech Stack

| Technology      | Usage                            |
| --------------- | -------------------------------- |
| Java 21         | Backend development              |
| Spring Boot     | REST API                         |
| Spring Data JPA | Database operations              |
| PostgreSQL      | Production database              |
| MySQL           | Local development, if configured |
| Next.js         | Frontend                         |
| TypeScript      | Frontend development             |
| Tailwind CSS    | UI styling                       |
| Docker          | Backend containerization         |
| Render          | Backend deployment               |
| Vercel          | Frontend deployment              |
| Supabase        | PostgreSQL hosting               |
| Postman         | API testing                      |
| Maven           | Backend build                    |

## Architecture

```text
                         User
                           |
                           v
                    Vercel Frontend
                    Next.js + TypeScript
                           |
                           | REST API
                           v
                    Render Backend
                    Spring Boot
                           |
                           v
                  Supabase PostgreSQL
```

### Short URL Flow

```text
Long URL
   |
   v
Frontend
   |
   v
POST /api/shorten
   |
   v
Spring Boot Backend
   |
   v
Generate Short Code
   |
   v
Save URL in Database
   |
   v
Return Short URL
```

### Redirect Flow

```text
User opens short URL
        |
        v
Spring Boot Backend
        |
        v
Find short code
        |
        v
Get original URL
        |
        v
Redirect to original URL
```

## Project Structure

```text
TinyURL/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   │
│   ├── Dockerfile
│   ├── pom.xml
│   └── ...
│
├── frontend/
│   ├── app/
│   ├── public/
│   ├── components/
│   ├── package.json
│   └── ...
│
├── postman/
│   └── TinyURL-API-Collection.json
│
├── README.md
└── .gitignore
```

## Backend

The backend is implemented using Spring Boot and exposes REST APIs for creating and resolving short URLs.

### Main API

#### Create Short URL

```http
POST /api/shorten
```

Example request:

```json
{
  "url": "https://www.example.com/very/long/url"
}
```

Example response:

```json
{
  "shortUrl": "https://your-domain/etssRq"
}
```

The generated short code is stored with the original URL in the database.

### Redirect

```http
GET /{shortCode}
```

Example:

```text
https://your-domain/etssRq
```

The backend finds the corresponding original URL and redirects the user.

> API paths and response fields should match the current controller implementation.

## Short Code Generation

The application generates a short code for every shortened URL.

The backend uses a secure random generation approach to create a short alphanumeric code.

Example:

```text
Long URL
https://example.com/some/long/path

        ↓

Short Code
etssRq

        ↓

Short URL
https://your-domain/etssRq
```

## Database

PostgreSQL is used for the deployed application.

The production database is hosted using Supabase.

A typical database record contains information such as:

```text
id
original_url
short_code
created_at
```

The exact fields depend on the current entity implementation.

## Environment Configuration

Sensitive configuration should be provided through environment variables instead of being committed to Git.

Example backend configuration:

```properties
DATABASE_URL=your_database_url
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
```

For the frontend:

```env
NEXT_PUBLIC_API_URL=your_backend_url
```

Do not commit passwords, database credentials, API keys, or other secrets to GitHub.

## Running Locally

### Prerequisites

Install:

* Java 21
* Maven
* Node.js
* npm
* PostgreSQL or a configured database
* Docker (optional)

### Backend

Move into the backend directory:

```bash
cd backend
```

Build the application:

```bash
mvn clean package
```

Run:

```bash
mvn spring-boot:run
```

The backend will start on the configured Spring Boot port.

### Frontend

Move into the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The Next.js application will be available on the local development URL.

## Running Backend with Docker

The backend contains a Dockerfile for containerization.

Build the Docker image:

```bash
docker build -t tinyurl-backend .
```

Run the container:

```bash
docker run -p 8080:8080 tinyurl-backend
```

The application can then be accessed through:

```text
http://localhost:8080
```

Database and environment configuration should be passed through environment variables when running the container.

## Postman Collection

A Postman collection is included in the repository for API testing.

```text
postman/
└── TinyURL-API-Collection.json
```

### Import Collection

1. Open Postman.
2. Select **Import**.
3. Select `TinyURL-API-Collection.json`.
4. Start the backend.
5. Configure the base URL if required.
6. Run the requests from the collection.

The collection can be used to test the URL shortening and redirect APIs.

## Deployment

### Frontend — Vercel

The Next.js frontend is deployed using Vercel.

```text
Next.js
   |
   v
Vercel
```

The frontend communicates with the deployed Spring Boot backend using the configured environment variable:

```env
NEXT_PUBLIC_API_URL
```

### Backend — Render

The Spring Boot backend is containerized using Docker and deployed on Render.

```text
Spring Boot
     |
     v
Docker Image
     |
     v
Render
```

### Database — Supabase

The production PostgreSQL database is hosted through Supabase.

```text
Render Backend
      |
      v
Supabase PostgreSQL
```

## Production Architecture

```text
                         Internet
                            |
                            v
                    ┌───────────────┐
                    │    Vercel     │
                    │   Next.js     │
                    └───────┬───────┘
                            |
                         REST API
                            |
                            v
                    ┌───────────────┐
                    │    Render     │
                    │ Spring Boot   │
                    │    Docker     │
                    └───────┬───────┘
                            |
                            v
                    ┌───────────────┐
                    │   Supabase    │
                    │  PostgreSQL   │
                    └───────────────┘
```

## API Testing

The application was tested using Postman during development.

Basic testing flow:

```text
1. Send long URL
        ↓
2. Generate short URL
        ↓
3. Verify short code
        ↓
4. Open short URL
        ↓
5. Verify redirect
```

## Error Handling

The backend handles common cases such as:

* Invalid URL requests
* Missing URL data
* Short code not found
* Database errors
* Invalid API requests

The API returns appropriate HTTP responses based on the request and application state.

## Security Considerations

* Database credentials are managed through environment variables.
* Production secrets are not stored in source control.
* CORS is configured for frontend-backend communication.
* Short codes are generated programmatically rather than using predictable sequential values.

## What I Learned

This project helped me work with several practical backend and deployment concepts:

* Spring Boot REST API development
* URL shortening logic
* JPA and database persistence
* PostgreSQL
* Random short-code generation
* Next.js frontend integration
* CORS configuration
* Docker containerization
* Render deployment
* Vercel deployment
* Supabase PostgreSQL
* Environment-based configuration
* API testing with Postman

## Future Improvements

Possible improvements for the project include:

* URL expiration
* Custom short URLs
* Click analytics
* Rate limiting
* Redis caching
* Authentication
* QR code generation
* URL validation improvements
* Automated testing
* CI/CD pipeline

## Author

**Sinku Singh**

Backend Developer | Java | Spring Boot | Microservices

GitHub: `JavaDeveloper-Sinku`
