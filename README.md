# global-exception-handler

Treating api errors is important to prevent loosing time debugging messages that doesn't present all the info.  

By using Spring, this project aims to create a generic and global way to throw a rest exception from anywhere in the source code. This exception, once not captured, can be viewed by the caller in the response body.

It treats basically three kinds of exceptions:
1) Validation based on the rules for each field received in the request.
2) Throw an exception like a business exception from anywhere in the source code.
3) Deeper exceptions not related to the source code.

The message below doesn't allow to discover immediately what is the problem. 

```
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Invalid request content.",
    "instance": "/api/users"
}
```

Using the present project, one can receive this message pattern for the same scenario:

```
Http Status: 400
{
    "code": "MP00101",
    "display": "MODAL",
    "title": "User creation",
    "description": "Invalid user data",
    "messages": [
        {
            "lastName": "Last Name is invalid"
        },
        {
            "email": "Email is invalid"
        }
    ]
}
```

In the endpoint below an exception is raised. You can see how easy it's to give details to the user:
```
@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto userDto) {
        throw new RestApiReportedException(NOT_IMPLEMENTED);
    }

}
```

Response:
```
Http Status 501
{
    "code": "MP00100",
    "display": "TOAST",
    "title": "User creation",
    "description": "Not implemented",
    "messages": [
        {
            "User creation": "Not implemented"
        }
    ]
}
```

Now imagine that the api call was made and the user receives the following json:
```
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Failed to read request",
    "instance": "/api/users"
}
```

In the scenario above, the request body has a json without a closing bracket. This project allows to make a reference to the exact error, making it easier to debug the problem:

```
Http Status: 400
{
    "code": "MP00005",
    "display": "FIX_BEFORE",
    "title": "Malformed request",
    "description": "JSON parse error: Unexpected end-of-input: expected close marker for Object (start marker at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 1])"
}

```

