package hello.service;

/** Service that produces {@link Greeting} messages. */
public interface GreetingService {

  int MAX_NAME_LENGTH = 50;

  /**
   * Generates a greeting message customized with the {@code name} provided.
   *
   * @param name name to use in the greeting message. Cannot be {@code null}, empty, or has more
   *     than {@value #MAX_NAME_LENGTH} characters.
   * @return {@link Greeting} object containing the greeting message
   * @throws IllegalArgumentException when {@code name} is invalid
   */
  Greeting greet(String name);
}
