# User microservice

## Description
User microservice is responsible for:
- managing users in MariaDB database
- CRUD on users
- providing verification of user credentials (`verifyCredentials` method)
- migrations

## Related with
- MariaDB
- `auth service`
 
## Api endpoints
### `/api/user/users`
*response*
```json
{

}
```

### `/api/auth/logout`
*body*
```json
{
    "token": "JWT-token"    
}
```
*response* \
Void

zeby zalogować to http://localhost:8082/login (user service) i podać {"username": "...", "password": "..."}
i potem ten token wygenerowany do headera Authorization i są odblokowane endpointy.

Zrobić tak żeby flight service brał token od user service