# Survey Example with Spring Boot and Angular
<img src="https://image.ibb.co/nMxqgH/Image3.png" width="500px" />

[![Build Status](https://travis-ci.org/joumenharzli/survey-example-spring-boot-angular.svg?branch=master)](https://travis-ci.org/joumenharzli/survey-example-spring-boot-angular)

## Overview
This is a survey example using Spring Boot, Spring JDBC, Bean Validation, i18n, MapStruct, Simple Flat Mapper, Angular 5, Reactive Forms, Angular Material and H2

## Architecture
<img src="https://image.ibb.co/bDRu8x/Image2.png" width="350px" />

## Domain Model
<img src="https://image.ibb.co/kN7Cvc/Survey_Domain_Model.png" />

## Launch
To launch the backend simply run 
```
$./backend/mvnw spring-boot:run
``` 

And to launch the frontend run 
```
$ ./frontend/yarn && yarn start
``` 

## Api Documentation 
You can access Swagger api documentation at 
```
http://${host}:${port}/swagger-ui.html
``` 
example
```
http://localhost:8080/swagger-ui.html
``` 
