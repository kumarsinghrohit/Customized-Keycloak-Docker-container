package com.auth.extensions.userregistration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.models.KeycloakSession;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class StoreEventListenerProviderFactoryTest {

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private StoreEventListenerProviderFactory storeEventListenerProviderFactory = new StoreEventListenerProviderFactory();

    @Before
    public void init(){
        environmentVariables.set("APPLICATION_URL", "http://test");
        storeEventListenerProviderFactory.init(mock(Config.Scope.class));
    }

    @Test
    public void testCreation() {
        EventListenerProvider eventListenerProvider = storeEventListenerProviderFactory.create(mock(KeycloakSession.class));
        assertThat(eventListenerProvider).isNotNull();
        assertThat(eventListenerProvider).extracting("storeClient").isEqualTo(new StoreClient(URI.create("http://test/api/v1/users")));
   }

    @Test
    public void testInvalidCreation() {
        environmentVariables.set("APPLICATION_URL", null);
        assertThatThrownBy(() -> {storeEventListenerProviderFactory.init(mock(Config.Scope.class));}).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testId(){
        assertThat(storeEventListenerProviderFactory.getId()).isEqualTo("store-user-registration-listener");
    }
}