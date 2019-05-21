package io.reflectoring.coderadar.rest.integration.analyzerconfig;

import static io.reflectoring.coderadar.rest.integration.ResultMatchers.containsResource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driver.analyzerconfig.get.GetAnalyzerConfigurationResponse;
import io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository.CreateAnalyzerConfigurationRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.rest.integration.ControllerTestTemplate;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ListAnalyzerConfigsFromProjectControllerIntegrationTest extends ControllerTestTemplate {

  @Autowired private CreateProjectRepository createProjectRepository;

  @Autowired private CreateAnalyzerConfigurationRepository createAnalyzerConfigurationRepository;

  @BeforeEach
  public void setUp() {
    Project testProject = new Project();
    testProject.setVcsUrl("https://valid.url");
    createProjectRepository.save(testProject);

    AnalyzerConfiguration analyzerConfiguration = new AnalyzerConfiguration();
    analyzerConfiguration.setProject(testProject);
    analyzerConfiguration.setAnalyzerName("analyzer");
    analyzerConfiguration.setEnabled(true);

    createAnalyzerConfigurationRepository.save(analyzerConfiguration);

    AnalyzerConfiguration analyzerConfiguration2 = new AnalyzerConfiguration();
    analyzerConfiguration2.setProject(testProject);
    analyzerConfiguration2.setAnalyzerName("analyzer2");
    analyzerConfiguration2.setEnabled(false);

    createAnalyzerConfigurationRepository.save(analyzerConfiguration2);
  }

  @Test
  void listAnalyzerConfigurationsFromProjectWithIdZero() throws Exception {
    mvc()
        .perform(get("/projects/0/analyzers"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(containsResource(GetAnalyzerConfigurationResponse[].class));
  }

  @Test
  void listAnalyzerConfigurationsReturnsErrorWhenProjectNotFound() throws Exception {
    mvc()
        .perform(get("/projects/1/analyzers"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("Project with id 1 not found."));
  }
}
