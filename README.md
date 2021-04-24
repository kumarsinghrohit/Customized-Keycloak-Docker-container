# My Application Keycloak

Customized Keycloak Docker container for My Application.

The prod version can be found as `my-application/keycloak`, the dev version as `my-application/keycloak-dev`.

The dev version containes a preconfigured realm and a default admin user `admin:admin`

# Extensions

The container contains an extension to notify the My Application application regarding new registered users.
Therfor the env variable `APPLICATION_URL` **must be set** with the URL to the right store endpoint, see store user management component documentation.

## Development

To implement themes or play with Keycloak settings just run `docker-compose up` and have a look at `docker-compose.yml`.
