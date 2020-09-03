package io.reflectoring.coderadar.rest.module;

import io.reflectoring.coderadar.projectadministration.port.driver.module.delete.DeleteModuleUseCase;
import io.reflectoring.coderadar.rest.AbstractBaseController;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class DeleteModuleController implements AbstractBaseController {
  private final DeleteModuleUseCase deleteModuleUseCase;
  private final AuthenticationService authenticationService;

  public DeleteModuleController(
      DeleteModuleUseCase deleteModuleUseCase, AuthenticationService authenticationService) {
    this.deleteModuleUseCase = deleteModuleUseCase;
    this.authenticationService = authenticationService;
  }

  @DeleteMapping(path = "/projects/{projectId}/modules/{moduleId}")
  public ResponseEntity<HttpStatus> deleteModule(
      @PathVariable(name = "moduleId") long moduleId,
      @PathVariable(name = "projectId") long projectId) {
    authenticationService.authenticateAdmin(projectId);
    deleteModuleUseCase.delete(moduleId, projectId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
