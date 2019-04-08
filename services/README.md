# Services

This module contains the following two components:

* `greetings`: a module that generates a simple greeting by combining a name with a quote retrieved
from the `quotes` component.
* `quotes`: a module that is used to store quotes and retrieve a random one.

Each of these services is composed of two parts:

* An `api` module that defines the external API of the component. That module contains only the
interfaces and exceptions required to support the API, no implementation classes. This is the
only dependency clients of the component should need at build time.
* An implementation module that contains the internal implementation for the component.

This approach allows the components to remain flexible and be packaged and deployed in many
different environments, making it possible to create clean OSGi API bundles, as well as client
libraries when deploying as a remote service.

Components in these modules are pure POJOs and have no dependencies on any external frameworks
(e.g., Spring, Swagger/OpenAPI, JAX-RS, etc.) and make no assumptions on the environment they run
in. This is achieved by injecting the proper framework and dependencies, which can be provided by
the platform and container the application is being deployed on and into.
