# Karaf Application

This module contains the different components needed to package and run the Quotes and Greetings
services inside Karaf.

## Running Quotes and Greetings Services

First, make sure the `services` and `osgi` modules have been built.

```bash
> cd multi-platform
> cd services
> mvn clean install
> cd ../osgi
> mvn clean install
```

### Install Karaf

To run the services, first [download](https://karaf.apache.org/download.html) and
[install](https://karaf.apache.org/manual/latest/#_quick_start) Karaf.

### Start the `greetings-app` Feature

Then start the container and install the required features

```bash
> cd <karaf_home>/bin
> ./karaf
> feature:repo-add mvn:com.connexta.multiplatform/osgi-greetings-endpoint-bundle/0.1.0/xml/features
> feature:install greetings-app
> feature:start greetings-app 0.1.0
```

### Test the Installation

To test that the service is up and running, point your browser at http://localhost:8080/greeting, or
run the following cURL:

```bash
> curl -X GET "http://localhost:8080/greeting?name=World" -H "accept: application/json"
```

## Metrics

Even though micrometer is included as part of the installation, metrics are currently _not_
collected or exposed.
