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
@Requires(classes = {DuplicateEmailException.class, ExceptionHandler.class})
public class DuplicateEmailHandler
    implements ExceptionHandler<DuplicateEmailException, HttpResponse<ErrorMessage>> {
  @Override
  public HttpResponse<ErrorMessage> handle(HttpRequest request, DuplicateEmailException exception) {
    ErrorMessage errorMessage =
        new ErrorMessage(HttpStatus.CONFLICT.getCode(), exception.getMessage());
    return HttpResponse.serverError(errorMessage).status(HttpStatus.CONFLICT);
  }
}
