package hello.service.impl;

import org.immutables.value.Value;

/**
 * Annotated interface used by https://immutables.org to generate the {@code ImmutableGreetingImpl}
 * class and builder that are used by {@link GreetingServiceImpl}.
 *
 * <p>This cannot be done in the {@code service-api} module as it would introduce a class in the
 * API module.
 */
@Value.Immutable
public interface GreetingImpl extends hello.service.Greeting {}
