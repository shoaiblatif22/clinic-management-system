------------------------------
# Clinical-Management-System #

Introduction:
 - This project has been inspired during the time I used to work in the NHS as a Radiographer, some of the software used was slow, inefficient, terrible UI and difficult to use. 
 - This is a multifaceted project. At the moment, this will be a GP system that will allow users to search data, patient records, drug charts, x-ray bookings. This is subject to change as I'm still deciding on some of the architechture and the direction the project will head in.

Tech stack:
- Backend => Java, Spring Boot, Keycloak with Spring Security auth
- CI/CD => GitHub actions for continuous integration of code/tests. Quodana for code quality
- DB => Adminer for db management (subject to change - possibly liquidbase in order to use changeset), Postgresql
- Frontend => Undecided => possibly typescript with react.
- Cloud/DevOps => Docker for containerisation, kubernetes for container management and AWS EC2 instances for cloud deployment.

------------------------------
# Get started
To run the backend and database, use the following two commands. Ensure you have docker desktop installed:
- Run **docker-compose -f Docker-Compose-db.yml up ** this will start the DB and BE application.
