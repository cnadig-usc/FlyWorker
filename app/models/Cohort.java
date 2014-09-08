package models;

import com.avaje.ebean.annotation.CreatedTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by johntower on 9/4/14.
 */
@Entity
@Table(name="cohort", uniqueConstraints = @UniqueConstraint(columnNames = {"cohort_name"}))
public class Cohort {

    @Id
    private Long cohort_id;

    @Column(nullable = false)
    private String cohort_name;

    @ManyToOne(fetch= FetchType.EAGER, optional = false)
    private Account created_by_account;

    @Column(nullable = false)
    private int number_of_vials;

    @Column(nullable = false)
    private int number_of_groups;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch= FetchType.EAGER, optional = false)
    private Experiment experiment;

    @CreatedTimestamp
    private Timestamp created_timestamp;

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public Account getCreated_by_account () {
        return created_by_account;
    }

    public Long getCohort_id() {
        return cohort_id;
    }

    public String getCohort_name() {
        return cohort_name;
    }

    public int getNumber_of_vials() {
        return number_of_vials;
    }

    public int getNumber_of_groups() {
        return number_of_groups;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCohort_id(Long cohort_id) {
        this.cohort_id = cohort_id;
    }

    public void setCohort_name(String cohort_name) {
        this.cohort_name = cohort_name;
    }

    public void setNumber_of_vials(int number_of_vials) {
        this.number_of_vials = number_of_vials;
    }

    public void setNumber_of_groups(int number_of_groups) {
        this.number_of_groups = number_of_groups;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated_timestamp(Timestamp created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public void setCreated_by_account(Account created_by_account) {
        this.created_by_account = created_by_account;
    }
}
