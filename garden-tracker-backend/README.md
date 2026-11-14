# Garden Tracker Backend API
Backend of the group project developed at Częstochowa University of Technology (Politechnika Częstochowska).

Team members: Oleksandr Kovalenko, Fabian Telęga, Yurii Levchuk.

Garden Tracker is a web application for managing plants, garden beds, and everyday gardening activities.
# Running the project
## Docker deployment
### Requirements
- Docker
- .env file in project directory
### Instructions
- docker-compose build ← build the application image
- docker-compose up -d ← start the containers
- docker ps -a ← check if all containers have successfully started
## Local deployment
### Requirements
- PostgreSQL instance
- .env file in project directory
### Instructions
- start the project from the IDE, the .env file is automatically injected
## .env file contents
- POSTGRES_DB ← name of the postgres database 
- POSTGRES_USER ← database user for authentication
- POSTGRES_PASSWORD ← database user's password for authentication
- JWT_SECRET ← jwt secret of at least 256 bytes
- JWT_EXPIRATION ← jwt expiration time in human-readable format e.g. 1h
### Documentation
Documentation available at the following endpoint: /swagger-ui.html
