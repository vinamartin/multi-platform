package quote.service;

/** Service that produces {@link Quote}s. */
public interface QuoteService {

  /** @return gets a random {@link Quote} object */
  Quote getQuote();

  /**
   * Adds a new {@link Quote}.
   *
   * @param author quote's author
   * @param content quote's content
   */
  Quote addQuote(String author, String content);
}
