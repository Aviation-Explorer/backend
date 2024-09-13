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