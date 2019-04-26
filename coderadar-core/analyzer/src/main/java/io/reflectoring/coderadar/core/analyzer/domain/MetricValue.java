package io.reflectoring.coderadar.core.analyzer.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity("HAS_VALUE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricValue {
  private Long id;

  @StartNode
  private File file;

  @EndNode
  private Metric metric;
}
