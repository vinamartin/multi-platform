package quote.service;

/** Service that produces {@link Quote}s. */
public interface QuoteService {

  /** @return gets a random {@link Quote} */
  Quote getQuote();

  /**
   * Adds a new {@link Quote}.
   *
   * @param author quote's author. Cannot be empty, or have more than {@value
   *     Quote#MAX_AUTHOR_LENGTH} characters.
   * @param content quote's content. Cannot be empty, or have more that {@value
   *     Quote#MAX_CONTENT_LENGTH} characters.
   * @throws IllegalArgumentException when {@code author} or {@code content} are invalid
   */
  Quote addQuote(String author, String content);
}
