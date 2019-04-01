package quote.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.immutables.value.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import quote.service.QuoteService;

@Api(value = "Quote Management API")
@RestController
public class QuoteController {
  private final QuoteService quoteService;

  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @ApiOperation(value = "Gets a random quote", response = QuoteModel.class)
  @GetMapping(value = "/quote", produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public QuoteModel getQuote() {
    return ImmutableQuoteModel.builder().from(quoteService.getQuote()).build();
  }

  @ApiOperation(value = "Adds a new quote", response = QuoteModel.class)
  @PostMapping(
      value = "/quote",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  @ResponseStatus(code = HttpStatus.CREATED)
  public QuoteModel addQuote(
      @ApiParam(name = "quote", value = "Quote to add to the store") @RequestBody
          AddQuoteModel newQuote) {
    return ImmutableQuoteModel.builder()
        .from(quoteService.addQuote(newQuote.author, newQuote.content))
        .build();
  }

  @ApiModel(description = "Quote to create")
  public static class AddQuoteModel {
    @ApiModelProperty(required = true, value = "Quote's author")
    public String author;

    @ApiModelProperty(required = true, value = "Quote's content")
    public String content;
  }

  @ApiModel(description = "Quote")
  @Value.Immutable
  public interface QuoteModel extends quote.service.Quote {
    @Override
    @ApiModelProperty("Quote's unique ID")
    Integer getId();

    @Override
    @ApiModelProperty("Quote's author")
    String getAuthor();

    @Override
    @ApiModelProperty("Quote's content")
    String getContent();
  }
}
