package authservice;

import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "test", version = "1.0"))
@OpenAPIInclude(classes = {
        io.micronaut.security.endpoints.LoginController.class,
        io.micronaut.security.endpoints.LogoutController.class
})
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}