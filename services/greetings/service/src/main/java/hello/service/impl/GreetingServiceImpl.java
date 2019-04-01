package hello.service.impl;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hello.service.Greeting;
import hello.service.GreetingService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import quote.service.Quote;
import quote.service.QuoteService;

public class GreetingServiceImpl implements GreetingService {
  private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceImpl.class);

  private static final String TEMPLATE = "Hello, %s! Here's your quote of the day:\"%s\" - %s";

  private final QuoteService quoteService;

  private final AtomicLong counter = new AtomicLong();

  private final Counter greetingsCounter;

  private final Timer greetingsTimer;

  public GreetingServiceImpl(QuoteService quoteService, MeterRegistry registry) {
    LOGGER.error("GreetingServiceImpl constructed");
    this.quoteService = quoteService;
    this.greetingsCounter = registry.counter("greetings.count");
    this.greetingsTimer = registry.timer("greetings.time");
  }

  @Override
  public Greeting greet(String name) {
    LOGGER.info("##### greet(\"{}\") called!", name);
    greetingsCounter.increment();

    Quote quote = quoteService.getQuote();

    String greetingMessage = String.format(TEMPLATE, name, quote.getContent(), quote.getAuthor());

    return greetingsTimer.record(
        () ->
            ImmutableGreeting.builder()
                .id(counter.incrementAndGet())
                .content(greetingMessage)
                .build());
  }
}
