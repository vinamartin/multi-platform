package quote.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.immutables.value.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import quote.service.Quote;
import quote.service.QuoteService;

/**
 * Spring Web controller class that exposes the {@code /quote} REST endpoint. This class' purpose is
 * to convert incoming REST requests into calls to the underlying {@link QuoteService} instance.
 *
 * <p>It also provides the REST endpoint documentation using Swagger 2 annotations.
 */
@Api(value = "Quote Management API")
@RestController
public class QuoteController {

  private final QuoteService quoteService;

  /**
   * Constructs a new REST Spring Web controller.
   *
   * @param quoteService service where requests will be forwarded
   */
  public QuoteController(QuoteService quoteService) {
    Preconditions.checkNotNull(quoteService, "QuoteService cannot be null");
    this.quoteService = quoteService;
  }

  /**
   * Gets a random quote from the list of quotes that have been added.
   *
   * @return new quote model that will be serialized out to JSON
   */
  @SuppressWarnings("unused")
  @ApiOperation(value = "Gets a random quote", response = QuoteModel.class)
  @GetMapping(value = "/quote", produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public QuoteModel getQuote() {
    return ImmutableQuoteModel.builder().from(quoteService.getQuote()).build();
  }

  /**
   * Adds a new quote to the list of quotes that can be retrieved.
   *
   * @param newQuote information about the new quote to add
   * @return new quote model that was created, including the new quote's unique ID
   */
  @SuppressWarnings("unused")
  @ApiOperation(value = "Adds a new quote", response = QuoteModel.class)
  @PostMapping(value = "/quote")
  @ResponseBody
  @ResponseStatus(code = HttpStatus.CREATED)
  public QuoteModel addQuote(
      @RequestBody @ApiParam(name = "quote", value = "Quote to add to the store") @Valid @NotNull
          AddQuoteModel newQuote) {
    return ImmutableQuoteModel.builder()
        .from(quoteService.addQuote(newQuote.author, newQuote.content))
        .build();
  }

  /**
   * Class that documents and defines the information needed to add a new quote and can be
   * serialized to JSON. Extends from the existing {@link Quote} interface to add Swagger 2
   * documentation.
   */
  @ApiModel(description = "Information about a new quote to add")
  public static class AddQuoteModel {
    @ApiModelProperty("Quote's author")
    @Valid
    @NotNull
    @Size(min = 1, max = Quote.MAX_AUTHOR_LENGTH)
    public String author;

    @ApiModelProperty("Quote's content")
    @Valid
    @NotNull
    @Size(min = 1, max = Quote.MAX_CONTENT_LENGTH)
    public String content;
  }

  /**
   * Class that documents and defines how quotes will be returned and serialized to JSON in the
   * responses. Extends from the existing {@link Quote} interface to add Swagger 2 documentation.
   */
  @ApiModel(description = "Quote")
  @Value.Immutable
  public interface QuoteModel extends quote.service.Quote {
    @Override
    @ApiModelProperty("Quote's unique ID")
    @Valid
    @NotNull
    Integer getId();

    @Override
    @ApiModelProperty("Quote's author")
    @Valid
    @NotNull
    @Size(min = 1, max = Quote.MAX_AUTHOR_LENGTH)
    String getAuthor();

    @Override
    @ApiModelProperty("Quote's content")
    @Valid
    @NotNull
    @Size(min = 1, max = MAX_CONTENT_LENGTH)
    String getContent();
  }
}
