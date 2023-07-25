# MessageSource

## Getting Started

### Problem Statement

Create a microservice to store and throw multiple exception from properties file
as per the different endpoint called.

### Curl Commands to Validate Bus Ticket Number

```
curl --location --request POST 'http://localhost:8080/validateBusTicket' \
--header 'ticketNumber: ABC345' \
--data ''
```
<u>Success Response</u>
```
Ticket Number Validation Successful : ABC345
```
</details>

<u>Error Response</u>
1. Missing ticket number
```json
{
  "errors": [
    {
      "errorCode": "101",
      "errorMessage": "Missing Bus Ticket Number"
    }
  ]
}
```
2. Invalid ticket number : ABC345645678
```json
{
  "errors": [
    {
      "errorCode": "102",
      "errorMessage": "Invalid Bus Ticket Number : Size must be less than 9"
    }
  ]
}
```
3. Invalid ticket number : 3456
```json
{
    "errors": [
        {
            "errorCode": "103",
            "errorMessage": "Invalid Bus Ticket Number : Must start with ABC"
        }
    ]
}
```
4. Invalid ticket number : 345645678234
```json
{
  "errors": [
    {
      "errorCode": "102",
      "errorMessage": "Invalid Bus Ticket Number : Size must be less than 9"
    },
    {
      "errorCode": "103",
      "errorMessage": "Invalid Bus Ticket Number : Must start with ABC"
    }
  ]
}
```
</details>

### Curl Commands to Validate Bus Ticket Number

```
curl --location --request POST 'http://localhost:8080/validateRailwayTicket' \
--header 'ticketNumber: ABC345' \
--data ''
```
<u>Success Response</u>
```
Ticket Number Validation Successful : ABC345
```
</details>

<u>Error Response</u>
1. Missing ticket number
```json
{
  "errors": [
    {
      "errorCode": "101",
      "errorMessage": "Missing Railway Ticket Number"
    }
  ]
}
```
2. Invalid ticket number : ABC345645678
```json
{
  "errors": [
    {
      "errorCode": "102",
      "errorMessage": "Invalid Railway Ticket Number : Size must be less than 9"
    }
  ]
}
```
3. Invalid ticket number : 3456
```json
{
    "errors": [
        {
            "errorCode": "103",
            "errorMessage": "Invalid Railway Ticket Number : Must start with ABC"
        }
    ]
}
```
4. Invalid ticket number : 345645678234
```json
{
  "errors": [
    {
      "errorCode": "102",
      "errorMessage": "Invalid Railway Ticket Number : Size must be less than 9"
    },
    {
      "errorCode": "103",
      "errorMessage": "Invalid Railway Ticket Number : Must start with ABC"
    }
  ]
}
```
</details>
