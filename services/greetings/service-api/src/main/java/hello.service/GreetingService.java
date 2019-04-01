package hello.service;

/** Service that produces {@link Greeting} messages. */
public interface GreetingService {

  /**
   * Generates a greeting message customized with the {@code name} provided.
   *
   * @param name name to use in the greeting message
   * @return {@link Greeting} object containing the greeting message
   */
  Greeting greet(String name);
}
