package io.reflectoring.coderadar.rest.useradministration;

import static io.reflectoring.coderadar.rest.GetUserResponseMapper.mapUser;

import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.rest.domain.GetUserResponse;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driver.get.GetUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class GetUserController implements AbstractBaseController {
  private final GetUserUseCase getUserUseCase;

  public GetUserController(GetUserUseCase getUserUseCase) {
    this.getUserUseCase = getUserUseCase;
  }

  @GetMapping(path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetUserResponse> getUser(@PathVariable long userId) {
    User user = getUserUseCase.getUser(userId);
    return new ResponseEntity<>(mapUser(user), HttpStatus.OK);
  }
}
