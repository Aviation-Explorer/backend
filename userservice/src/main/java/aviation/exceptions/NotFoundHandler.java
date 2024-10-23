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
@Requires(classes = {NotFoundException.class, ExceptionHandler.class})
public class NotFoundHandler implements ExceptionHandler<NotFoundException, HttpResponse<ErrorMessage>> {
    @Override
    public HttpResponse<ErrorMessage> handle(HttpRequest request, NotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.getCode(), exception.getMessage());
        return HttpResponse.serverError(errorMessage).status(HttpStatus.NOT_FOUND);
    }
}
