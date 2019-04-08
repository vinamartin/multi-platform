# Multi-Platform Sample Repository

Repository that provides an example of how components can be written to be packaged and deployed
different ways, i.e., as Spring applications, Docker containers, inside Karaf (OSGi container), etc.

The following top-level modules provide information about different independent pieces of the
overall sample application and possible deployment options.

**services**

The `services` module contains the different service components. Each service component is
packaged and deployed in different forms by the other modules.

**osgi**

The `osgi` module packages the components defined in the `services` as OSGi bundles that can be
deployed as OSGi services inside an OSGi container.

**spring**

The `spring` module packages the `services` components as Spring Boot applications.

**docker**

The `docker` module packages the Spring Boot applications created by the `spring` module as
Docker images and provides a Docker Compose configuration file to run them as a multi-service
deployment under Docker.

## Third-Party Libraries and Frameworks

The code in these modules use [Immutables](https://immutables.org) to simplify the creation of
immutable classes and objects.

They also use the [Micrometer](https://micrometer.io) generic interfaces to capture metrics.

## Building Multi-Platform

To build all modules, simply install Maven 3.5 or later and run

```bash
> mvn install
```

Each module describes how to run their specific version of the service components.
