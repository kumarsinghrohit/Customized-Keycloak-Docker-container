package com.auth.extensions.userregistration;

import org.jboss.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Objects;

class StoreClient {

    private static Logger LOGGER = Logger.getLogger(StoreClient.class.getName());

    private URI storeUrl;

    StoreClient(URI storeUrl){
        this.storeUrl = Objects.requireNonNull(storeUrl);
    }

    int registerUser(RegisteredUser registeredUser){
        Client client = ClientBuilder.newClient();
        LOGGER.debug("send user data to "+storeUrl.toString());
        WebTarget webTarget = client.target(storeUrl);
        Response response = webTarget.request().post(Entity.entity(registeredUser, MediaType.APPLICATION_JSON_TYPE));
        LOGGER.debug("response: "+response.getStatus());
        return response.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreClient that = (StoreClient) o;
        return storeUrl.equals(that.storeUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeUrl);
    }
}
