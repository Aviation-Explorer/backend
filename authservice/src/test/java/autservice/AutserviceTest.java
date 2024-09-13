package autservice;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest(startApplication = false)
class AutserviceTest {

    @Inject
    EmbeddedApplication<?> application;

    
}
