package quote.service.impl;

import org.immutables.value.Value;

import com.google.common.base.Preconditions;

/**
 * Annotated abstract class used by https://immutables.org to generate the {@code
 * ImmutableQuoteImpl} class and builder that are used by {@link QuoteServiceImpl}.
 *
 * <p>This cannot be done in the {@code service-api} module as it would introduce a class in the API
 * module.
 */
@Value.Immutable
public abstract class QuoteImpl implements quote.service.Quote {

  /**
   * Called by the Immutables generated code to validate the different attributes upon creation.
   * Instantiation will fail if this method throws an exception.
   */
  @Value.Check
  void check() {
    validateAuthor(getAuthor());
    validateContent(getContent());
  }

  private void validateAuthor(final String author) {
    Preconditions.checkArgument(
        (author != null) && !author.trim().isEmpty() && (author.length() <= MAX_AUTHOR_LENGTH),
        "Author cannot be null, empty, or have more than %d characters",
        MAX_AUTHOR_LENGTH);
  }

  private void validateContent(final String content) {
    Preconditions.checkArgument(
        (content != null) && !content.isEmpty() && (content.length() < MAX_CONTENT_LENGTH),
        "Author cannot be null, empty, or have more than %d characters",
        MAX_CONTENT_LENGTH);
  }
}
