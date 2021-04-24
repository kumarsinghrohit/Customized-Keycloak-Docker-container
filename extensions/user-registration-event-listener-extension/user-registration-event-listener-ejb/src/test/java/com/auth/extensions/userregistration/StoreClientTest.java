package com.auth.extensions.userregistration;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@PrepareForTest(ClientBuilder.class)
@RunWith(PowerMockRunner.class)
public class StoreClientTest {

    @Mock
    private Response response;
    @Mock
    private Invocation.Builder request;

    @Before
    public void init(){
        Client client = mock(Client.class);

        PowerMockito.mockStatic(ClientBuilder.class);
        when(ClientBuilder.newClient()).thenReturn(client);

        WebTarget webTarget = mock(WebTarget.class);
        when(client.target(URI.create("http://testUrl"))).thenReturn(webTarget);
        when(webTarget.request()).thenReturn(request);

        when(request.post(Entity.entity(new RegisteredUser("test1", "test@test.de"), MediaType.APPLICATION_JSON_TYPE))).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
    }

    @Test
    public void testClient(){
        StoreClient storeClient = new StoreClient(URI.create("http://testUrl"));
        int responseCode = storeClient.registerUser(new RegisteredUser("test1", "test@test.de"));
        verify(request).post(Entity.entity(new RegisteredUser("test1", "test@test.de"), MediaType.APPLICATION_JSON_TYPE));
        assertThat(responseCode).isEqualTo(200);
    }

}