# Spring Boot Applications

This module contains the different components needed to package and run the Quotes and Greetings
services as Spring Boot applications.

The applications can currently be run two different ways. As a single Spring Boot application that
includes both the Quotes and Greetings services, or as two independent Spring Boot applications
that communicate with each other over a REST endpoint.

## General Notes

To keep the business logic code as independent from Spring as possible, all injection is performed
using Spring's [Java-based Container Configuration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-java)
instead of Spring or JSR-330 annotations.

It is recommended to keep dependency injection annotation usage to a minimum in business logic code.
However, if they need to be used, only the standard ones (as defined by
[JSR-330](https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/core.html#beans-standard-annotations))
should be used. The `@Named` annotation should be used along with `@Inject` to clearly specify what
dependency will be injected and eliminate the risk of bad injection inference from the DI framework.

Spring's `@Autowire` should be avoided as it relies solely on Spring's type-based dependency
inference to perform injection, which sometimes results in unexpected wiring.

## Running Quotes and Greetings Services

First, make sure the `services` and `spring` modules have been built.

```bash
> cd multi-platform
> cd services
> mvn clean install
> cd ../spring
> mvn clean install
```

### Running as Independent Applications

#### Running the Quotes Application

Once all the modules have been built, run the Quotes application:

```bash
> cd quotes-app
> java -Dserver.port=4001 -jar target/spring-quotes-app-0.1.0.jar
```

To test that the service is up and running, point your browser at http://localhost:4001/quote, or
run the following cURL:

```bash
> curl -X GET "http://localhost:4001/quote" -H "accept: application/json"
```

You can also take a look at the Swagger documentation at http://localhost:4001/swagger-ui.html.

#### Running the Greetings Application

Once the Quotes application is up and running, start the Greetings application:

```bash
> cd ../greetings-app
> java -Dserver.port=4000 -Dservices.quote.url="http://localhost:4001" -jar target/spring-greetings-app-0.1.0.jar
```

To test that the service is up and running, point your browser at http://localhost:4000/greeting, or
run the following cURL:

```bash
> curl -X GET "http://localhost:4000/greeting?name=World" -H "accept: application/json"
```

You can also take a look at the Swagger documentation at http://localhost:4000/swagger-ui.html.

### Running as a Single Applications

Another way to run the Quotes and Greetings services is to run them as a single application.

To do so, run the `single-app`:

```bash
> cd single-app
> java -Dserver.port=8080 -jar target/spring-single-app-0.1.0.jar
```

To test that the service is up and running, point your browser at http://localhost:8080/greeting, or
run the following cURL:

```bash
> curl -X GET "http://localhost:8080/greeting?name=World" -H "accept: application/json"
```

You can also take a look at the Swagger documentation at http://localhost:8080/swagger-ui.html.

## Metrics

Metrics are exposed using JMX and can be accessed using JConsole and connecting to the
running application(s).
