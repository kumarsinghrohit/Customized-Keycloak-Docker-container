package com.auth.extensions.userregistration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * EventListenerProvider for my application communication.
 */
public class StoreEventListenerProvider implements EventListenerProvider {

    private static final Logger LOGGER = Logger.getLogger(StoreEventListenerProvider.class.getName());
    private static final String FIELD_USERNAME = "username";
    private static final String REALM_ID = "MyApplication";


    private KeycloakSession keycloakSession;
    private StoreClient storeClient;

    /**
     * Creates a StoreEventListenerProvider instance using the given storeURL.
     * @param storeClient HTTP client for communication to the store application
     */
    public StoreEventListenerProvider(KeycloakSession keycloakSession, StoreClient storeClient) {
        this.keycloakSession = keycloakSession;
        this.storeClient = Objects.requireNonNull(storeClient);
    }

    @Override
    public void onEvent(Event event) {
        if(event.getType() == EventType.REGISTER) {
            registerUser(new RegisteredUser(event.getUserId(), event.getDetails().get(FIELD_USERNAME)));
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        if(event.getOperationType() == OperationType.CREATE && event.getResourceType() == ResourceType.USER){
           registerUser(readUser(event));
        }
    }

    private RegisteredUser readUser(AdminEvent event){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> eventMap;
        try {
            eventMap = objectMapper.readValue(event.getRepresentation(), Map.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("user not readable!", e);
        }
        String email = eventMap.get(FIELD_USERNAME); //email is stored as user name for the current implementation
        LOGGER.debug("retrieve user for email '"+email+"'");
        UserModel userModel = keycloakSession.users().getUserByEmail(email, keycloakSession.realms().getRealm(REALM_ID));
        LOGGER.debug("got user with id "+userModel.getId());
        return new RegisteredUser(userModel.getId(), eventMap.get(FIELD_USERNAME));
    }

    private void registerUser(RegisteredUser registeredUser){
        LOGGER.info("User '"+registeredUser.getEmail()+"' registered, id '"+ registeredUser.getId()+"'");
        int responseStatus = storeClient.registerUser(registeredUser);
        LOGGER.info("application notified, statusCode: "+responseStatus);
    }


    @Override
    public void close() {
        // nothing to do
    }

}
