# Auth microservice

## Description
Auth microservice is responsible for:
- generating JWT token and store it in Redis based on response from `verifyCredentials -> Mono<Boolean>`

## Related with
- Redis
- `user service`
 

## Api endpoints
### `/api/auth/login`
*body*
```json
{
    "username": "user.email@example.com",
    "password": "userPassword"
}
```
*response* \
Generated JWT token

### `/api/auth/logout`
*body*
```json
{
    "token": "JWT-token"    
}
```
*response* \
Void