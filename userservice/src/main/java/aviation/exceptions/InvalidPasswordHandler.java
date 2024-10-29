package aviation.exceptions;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {InvalidPasswordException.class, ExceptionHandler.class})
public class InvalidPasswordHandler
    implements ExceptionHandler<InvalidPasswordException, HttpResponse<ErrorResponse>> {
  @Override
  public HttpResponse<ErrorResponse> handle(
      HttpRequest request, InvalidPasswordException exception) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.getCode(), exception.getMessage());
    return HttpResponse.serverError(errorResponse).status(HttpStatus.BAD_REQUEST);
  }
}
