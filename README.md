# Salamander
Repository for frontend + backend for Team Manders

## Frontend
Libraries Used:
 - [LeafletJS](https://leafletjs.com/) - for interacting with the map GUI
 - [MapBox](https://www.mapbox.com/) - provides Leaflet with the actual maps
 - jQuery - to do fun stuff with the HTML
 - Bootstrap - makes the buttons look pretty!

## Backend
Written in Java (JDK 11) and connected to a MySQL database
Libraries Used:
 - [Spring Boot](https://spring.io/) - bootstraps a spring application!
 - [Hibernate/JPA](https://hibernate.org/) - persistence layer for "persisting" our Java objects
 - [JTS - Java Topology Suite](https://locationtech.github.io/jts/) - a great open-sourced library for operating on geometry objects
 - [jts2geojson](https://github.com/bjornharrtell/jts2geojson) - or translating GeoJSON string to JTS objects (JTS does not have a native GeoJSON parser)
 - [Maven](https://maven.apache.org/) - came with spring boot, but super useful for managing dependencies!
 
 ## How to Run
  - First `git clone` the repo
  - Navigate to Salamander/src/main/resources/application.properties and configure a connection to your MySQL DB
  - Run `mvn spring-boot:run` in your terminal
  - Open a browser and go to `localhost:8080`
  
  ** Note ** This repo does not contain any data for the database. If you want our data check the downloads section for a MySQL dump of our database
