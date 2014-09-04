package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by johntower on 9/4/14.
 */
@Entity
@Table(
        name="experiment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"experiment_name"})
)
public class Experiment {

    @Id
    private Long experiment_id;

    private String experiment_name;

    public Long getExperiment_id() {
        return experiment_id;
    }

    public void setExperiment_id(Long experiment_id) {
        this.experiment_id = experiment_id;
    }
}
