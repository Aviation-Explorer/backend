package authservice.exceptions;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {NoEmailExistsException.class, ExceptionHandler.class})
public class NoEmailExistsHandler
    implements ExceptionHandler<NoEmailExistsException, HttpResponse<ErrorResponse>> {
  @Override
  public HttpResponse<ErrorResponse> handle(HttpRequest request, NoEmailExistsException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.getCode(), exception.getMessage());
    return HttpResponse.serverError(errorResponse).status(HttpStatus.NOT_FOUND);
  }
}
