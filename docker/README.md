# Docker Deployment

This module and sub-modules contain the build infrastructure to create Docker images for the
Greetings and Quotes applications, as well as the Docker Compose file to run them.

## Building the Docker Images

Before you can start the applications inside Docker, make sure the `services`, `spring` and `docker`
modules have been built.
                                            
```bash
> cd multi-platform
> cd services
> mvn clean install
> cd ../spring
> mvn clean install
> cd ../docker
> mvn clean install
```

### Known Build Issues

If the `docker` module fails to build with the following error:

```text
[ERROR] Failed to execute goal com.spotify:dockerfile-maven-plugin:1.3.6:build (default) on project docker-quotes: Could not build image: com.spotify.docker.client.shaded.com.fasterxml.jackson.databind.JsonMappingException: Can not construct instance of com.spotify.docker.client.messages.RegistryAuth: no String-argument constructor/factory method to deserialize from String value ('swarm')
[ERROR]  at [Source: N/A; line: -1, column: -1] (through reference chain: java.util.LinkedHashMap["stackOrchestrator"])
```

Remove the `"stackOrchestrator" : "swarm"` line contained in your `config.json` Docker configuration
file located under `$HOME/.docker`.

## Running the Applications

Once the Docker images have been successfully built, you can start the applications using Docker
Compose:

```bash
> docker-compose up
```

### Accessing the Quotes Application

The Quotes application should now be available using port 4001 and accessible using
http://localhost:4001/quote or cURL:

```bash
> curl -X GET "http://localhost:4001/quote" -H "accept: application/json"
```

You can also take a look at the Swagger documentation at http://localhost:4001/swagger-ui.html.

### Accessing the Greetings Applications

The Greetings application should be available on port 4000 and accessible at
http://localhost:4000/greeting, or cURL:

```bash
> curl -X GET "http://localhost:4000/greeting?name=World" -H "accept: application/json"
```

You can also take a look at the Swagger documentation at http://localhost:4000/swagger-ui.html.

### Application Metrics

The two running Docker containers will forward the ports required to access JMX from your local
host. However, JMX also needs the hostname or IP of the host running the containers, which is
not done automatically in the current `Dockerfile`.

If access to the metrics is needed, please update the `java.rmi.server.hostname` property in the
`Dockerfile` with your host's IP address.
