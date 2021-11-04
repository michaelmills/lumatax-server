# lumatax-server

Spring Boot server for LumaTax take home assessment.

### Notable libraries / services used:
    * Java 11
    * Spring Boot
    * Spring Security
    * Spring JPA
    * Lombok
    * H2 database
    * OpenCSV
    * Docker

---

## Build Spring Boot server
1. Run: mvn clean install  
   This will build and create the docker image
2. You can either run Docker here or Docker-compose in https://github.com/michaelmills/lumatax/tree/main/docker
    * Docker run command:  
      docker run --publish 8082:80 --env-file ./lumatax-server/lumatax-server.env mrmills/lumatax-server:1.0.0
    * Docker compose command:  
      docker-compose up (or docker-compose up -d for detached mode)
