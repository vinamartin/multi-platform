package quote.service;

/** Quote. */
public interface Quote {
  /** Maximum number of characters that can be used for the Author name */
  int MAX_AUTHOR_LENGTH = 50;

  /** Maximum quote length */
  int MAX_CONTENT_LENGTH = 250;

  /** @return unique ID associated with the quote */
  Integer getId();

  /**
   * @return quote's author. Never {@code null}, empty, or more than {@value #MAX_AUTHOR_LENGTH}
   *     character long
   */
  String getAuthor();

  /**
   * @return quote's text. Never {@code null}, empty, or more than {@value #MAX_CONTENT_LENGTH}
   *     character long
   */
  String getContent();
}
