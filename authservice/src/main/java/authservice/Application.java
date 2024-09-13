package authservice;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import static io.micronaut.context.env.Environment.DEVELOPMENT;

@OpenAPIDefinition(
    info = @Info(
        title = "test",
        version = "1.0"
    )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.build(args)
                .mainClass(Application.class)
                .defaultEnvironments(DEVELOPMENT)
                .start();
    }
}