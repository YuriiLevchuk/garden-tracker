# Running the project
### Requirements
- Docker
### Instructions
- docker-compose build - build the application image
- docker-compose up -d - start the containers
- docker ps - check if all containers have successfully started

# Authentication
The API uses JWT tokens. Set the `JWT_SECRET` environment variable (at least 32 characters) outside local development; `docker-compose.yml` has a default for local use only.

| Endpoint | Auth | Description |
|---|---|---|
| POST /api/auth/register | no | create an account (`email`, `password`), returns `token` and `user` |
| POST /api/auth/login | no | log in (`email`, `password`), returns `token` and `user` |
| GET /api/users/me | yes | return the logged-in user |

Send the token in the `Authorization: Bearer <token>` header. Every other endpoint requires it by default.
