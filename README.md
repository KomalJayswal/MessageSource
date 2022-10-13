# MessageSource

## Getting Started

### Problem Statement

Create a microservice to throw a validation message from properties file 
as per the endpoint called.

### Solution

### Improvements

* Craete a use case and add descriptions
* Add java comments
* Add standard variable and method names
* Segreated in standard packages

### Read on
* spring.main.allow-bean-definition-overriding
* MessageSourceUtils

### Curl Commands

```
curl --location --request POST 'http://localhost:8080/validateFirstScreen' \
--header 'Content-Type: application/json' \
--header 'flag: true' \
--header 'Cookie: JSESSIONID=5465B0BB022F81E4DAAADA5599DD0589' \
--data-raw ''
```

```
curl --location --request POST 'http://localhost:8080/validateSecondScreen' \
--header 'Content-Type: application/json' \
--header 'flag: true' \
--header 'Cookie: JSESSIONID=5465B0BB022F81E4DAAADA5599DD0589' \
--data-raw ''
```
