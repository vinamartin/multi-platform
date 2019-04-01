package hello.service;

/** Greeting message. */
public interface Greeting {

  /** @return unique ID associated with the greeting. */
  long getId();

  /** @return greeting message. */
  String getContent();
}
