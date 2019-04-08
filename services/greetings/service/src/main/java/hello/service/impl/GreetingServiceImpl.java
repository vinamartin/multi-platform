package hello.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import hello.service.Greeting;
import hello.service.GreetingService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import quote.service.Quote;
import quote.service.QuoteService;

/**
 * Implementation of the {@link GreetingService} interface that creates immutable {@link Greeting}
 * objects.
 *
 * <p>This class shows how <a href="micrometer.io">micrometer</a> can be used to collect service
 * specific metrics. See <a
 * href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-metrics">Spring
 * Boot Metrics</a> for more information.
 */
public class GreetingServiceImpl implements GreetingService {
  private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceImpl.class);

  private static final String TEMPLATE = "Hello, %s! Here's your quote of the day:\"%s\" - %s";

  private final QuoteService quoteService;

  // Counter that captures the number of greetings requested
  private final Counter greetingsCounter;

  // Timer that captures how long it took to generate a greeting
  private final Timer greetingsTimer;

  /**
   * Constructs a new {@link GreetingServiceImpl} instance.
   *
   * @param quoteService reference to the {@link QuoteService} that will be used to get the random
   *     quotes to add to the greeting messages
   * @param registry Micrometer registry used to create metrics objects
   */
  public GreetingServiceImpl(final QuoteService quoteService, final MeterRegistry registry) {
    Objects.requireNonNull(quoteService, "Quote service cannot be null");
    Objects.requireNonNull(registry, "Metrics registry cannot be null");

    this.quoteService = quoteService;
    this.greetingsCounter = registry.counter("greetings.count");
    this.greetingsTimer = registry.timer("greetings.time");
  }

  @Override
  public Greeting greet(final String name) {
    Preconditions.checkArgument(
        (name != null) && !name.isEmpty() && (name.length() <= GreetingService.MAX_NAME_LENGTH),
        "Name cannot be null, empty, or have more than %s characters",
        MAX_NAME_LENGTH);

    LOGGER.info("Generating greeting for {}", name);

    // Capturing metric on how many greetings are being generated
    greetingsCounter.increment();

    // Capturing metric on how long it takes to generate a new greeting
    return greetingsTimer.record(
        () ->
            ImmutableGreetingImpl.builder()
                .id(UUID.randomUUID().getLeastSignificantBits())
                .content(getGreetingMessage(name, quoteService.getQuote()))
                .build());
  }

  private String getGreetingMessage(String name, Quote quote) {
    return String.format(TEMPLATE, name, quote.getContent(), quote.getAuthor());
  }
}
