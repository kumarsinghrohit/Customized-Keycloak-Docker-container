package com.auth.extensions.userregistration;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.net.URI;
import java.util.Objects;

/**
 * Factory for StoreEventListenerProvider instances.
 * <br/>
 * The env variable <i>APPLICATION_URL</i> is mandatory and must point to the Application instance. <br/>
 * The default users endpoint '/api/v1/users' can be changed using the env variable <i>APPLICATION_USERS_PATH</i>
 *
 */
public class StoreEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final Logger LOGGER = Logger.getLogger(StoreEventListenerProviderFactory.class.getName());
    private static final String ENV_APPLICATION_URL = "APPLICATION_URL";
    private static final String ENV_APPLICATION_USERS_PATH = "APPLICATION_USERS_PATH";
    private static final String DEFAULT_USERS_ENDPOINT = "/api/v1/users";

    private URI storeURL;

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new StoreEventListenerProvider(keycloakSession, new StoreClient(Objects.requireNonNull(storeURL, ENV_APPLICATION_URL+" must not be null!")));
    }

    /**
     * Creates the right URL for the StoreClient.
     * @param scope keycloak config scope
     */
    @Override
    public void init(Config.Scope scope) {
        String applicationUrl = System.getenv(ENV_APPLICATION_URL);
        Objects.requireNonNull(applicationUrl, ENV_APPLICATION_URL+" must not be null!");
        String usersEndPoint = null;
        if(System.getenv(ENV_APPLICATION_USERS_PATH) != null) {
            usersEndPoint = System.getenv(ENV_APPLICATION_USERS_PATH);
        } else {
            usersEndPoint = DEFAULT_USERS_ENDPOINT;
        }
        storeURL = URI.create(applicationUrl+usersEndPoint);
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        LOGGER.info("Application Store URL: "+storeURL.toString());
    }

    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public java.lang.String getId() {
        return "store-user-registration-listener";
    }
}
