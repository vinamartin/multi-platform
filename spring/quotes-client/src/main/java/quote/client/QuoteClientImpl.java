package quote.client;

import java.net.URL;
import java.util.Map;

import org.immutables.value.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;

import quote.service.Quote;
import quote.service.QuoteService;

/**
 * Client class that abstracts the Quote REST endpoint. Java clients can use this class instead of
 * writing the HTTP REST client code themselves.
 */
public class QuoteClientImpl implements QuoteService {

  private final URL serviceUrl;

  /**
   * Constructs a new Quote REST client instance.
   *
   * @param serviceUrl URL of the quotes service
   */
  public QuoteClientImpl(URL serviceUrl) {
    this.serviceUrl = serviceUrl;
  }

  @Override
  public Quote getQuote() {
    RestTemplate quoteRestTemplate = new RestTemplate();
    ResponseEntity<ImmutableQuoteModel> quoteResponse =
        quoteRestTemplate.getForEntity(serviceUrl + "/quote", ImmutableQuoteModel.class);

    if (quoteResponse.getStatusCode() != HttpStatus.OK) {
      // TODO - Check error code and throw appropriate exception
      throw new RuntimeException(quoteResponse.toString());
    }

    return quoteResponse.getBody();
  }

  @Override
  public Quote addQuote(String author, String content) {
    RestTemplate quoteRestTemplate = new RestTemplate();
    Map<String, String> newQuote = ImmutableMap.of("author", author, "content", content);
    ResponseEntity<Quote> quoteResponse =
        quoteRestTemplate.postForEntity(serviceUrl + "/quote", newQuote, Quote.class);

    if (quoteResponse.getStatusCode() != HttpStatus.OK) {
      // TODO - Check error code and throw appropriate exception
      throw new RuntimeException(quoteResponse.toString());
    }

    return quoteResponse.getBody();
  }

  /**
   * Class that extends from the existing {@link Quote} interface defines how quotes will be
   * serialized to JSON.
   *
   * <p>For more complex models, it is recommended to create a separate module that contains all the
   * model classes and have the endpoint and client library depend on that same common model instead
   * of defining it here and in the endpoint module.
   */
  @Value.Immutable
  @JsonSerialize(as = ImmutableQuoteModel.class)
  @JsonDeserialize(as = ImmutableQuoteModel.class)
  public interface QuoteModel extends Quote {}
}
