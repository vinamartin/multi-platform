package quote.service;

/** Quote. */
public interface Quote {

  /** @return unique ID associated with the quote. */
  Integer getId();

  /** @return quote's author */
  String getAuthor();

  /** @return quote's text */
  String getContent();
}
