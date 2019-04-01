package hello.endpoint;

import org.immutables.value.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.service.Greeting;
import hello.service.GreetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** Controller class for Spring Web implementation. */
@Api(value = "Greetings web service")
@RestController
public class GreetingController {

  private final GreetingService greetingService;

  public GreetingController(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  @ApiOperation(value = "Generates a greeting", response = GreetingModel.class)
  @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public GreetingModel greeting(
      @RequestParam(value = "name", defaultValue = "World") @ApiParam("Name to add to the greeting")
          String name) {
    return ImmutableGreetingModel.builder().from(greetingService.greet(name)).build();
  }

  @ApiModel(description = "Greeting")
  @Value.Immutable
  public interface GreetingModel extends Greeting {

    @Override
    @ApiModelProperty("Greeting's unique ID")
    long getId();

    @Override
    @ApiModelProperty("Greeting'' content")
    String getContent();
  }
}
