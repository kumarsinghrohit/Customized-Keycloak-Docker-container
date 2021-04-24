package com.auth.extensions.userregistration;

import org.junit.Test;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class StoreEventListenerProviderTest {

    private StoreClient storeClient = mock(StoreClient.class);
    private KeycloakSession keycloakSession = mock(KeycloakSession.class);
    private StoreEventListenerProvider storeEventListenerProvider = new StoreEventListenerProvider(keycloakSession, storeClient);

    @Test
    public void testOnRegisterEvent(){
        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.REGISTER);
        when(event.getUserId()).thenReturn("0815");
        Map<String, String> detailsMap = new HashMap<>();
        detailsMap.put("username", "test@test.com");
        when(event.getDetails()).thenReturn(detailsMap);

        storeEventListenerProvider.onEvent(event);

        verify(storeClient).registerUser(new RegisteredUser(event.getUserId(), event.getDetails().get("username")));
    }

    @Test
    public void testOnOtherEvents(){
        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.LOGOUT);

        storeEventListenerProvider.onEvent(event);

        verifyZeroInteractions(storeClient);
    }

    @Test
    public void testOnAdminEvent(){
        AdminEvent adminEvent = mock(AdminEvent.class);
        when(adminEvent.getOperationType()).thenReturn(OperationType.CREATE);
        when(adminEvent.getResourceType()).thenReturn(ResourceType.USER);
        when(adminEvent.getRepresentation()).thenReturn("{\"username\":\"test@test.de\",\"enabled\":true,\"email\":\"test@test.de\",\"attributes\":{}}");

        RealmProvider realmProvider = mock(RealmProvider.class);
        RealmModel realmModel = mock(RealmModel.class);
        when(realmProvider.getRealm("MyApplication")).thenReturn(realmModel);
        when(keycloakSession.realms()).thenReturn(realmProvider);
        UserProvider userProvider = mock(UserProvider.class);
        when(keycloakSession.users()).thenReturn(userProvider);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn("12345");
        when(userProvider.getUserByEmail("test@test.de", realmModel)).thenReturn(userModel);

        storeEventListenerProvider.onEvent(adminEvent, true);

        verify(storeClient).registerUser(new RegisteredUser("12345","test@test.de"));
    }

    @Test
    public void testOnOtherAdminEvents(){
        AdminEvent adminEvent = mock(AdminEvent.class);
        when(adminEvent.getOperationType()).thenReturn(OperationType.CREATE);
        when(adminEvent.getResourceType()).thenReturn(ResourceType.COMPONENT);

        storeEventListenerProvider.onEvent(adminEvent, true);

        AdminEvent adminEvent2 = mock(AdminEvent.class);
        when(adminEvent2.getOperationType()).thenReturn(OperationType.UPDATE);
        when(adminEvent2.getResourceType()).thenReturn(ResourceType.USER);

        storeEventListenerProvider.onEvent(adminEvent, true);

        verifyZeroInteractions(storeClient);
    }
}