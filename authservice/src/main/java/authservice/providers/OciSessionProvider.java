package authservice.providers;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.email.javamail.sender.MailPropertiesProvider;
import io.micronaut.email.javamail.sender.SessionProvider;
import jakarta.inject.Singleton;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;


@Slf4j
@Singleton
public class OciSessionProvider implements SessionProvider {
    private final Properties properties;
    private final String user;
    private final String password;


    public OciSessionProvider(MailPropertiesProvider properties,
                              @Property(name = "aviation.smtp.user") String user,
                              @Property(name = "aviation.smtp.password") String password) {
        this.properties = properties.mailProperties();
        this.user = user;
        this.password = password;
    }

    @Override
    public @NonNull Session session() {

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }
}
