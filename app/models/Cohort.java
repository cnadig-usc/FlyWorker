package models;

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

    private String cohort_name;

    private String created_by_user;

    private Account created_by_account;

    private int number_of_vials;

    private int number_of_groups;

    private String status;

    private Long experiment_id;

    private Experiment experiment;

    private Timestamp created_timestamp;

    public String getCreated_by_user() {
        return created_by_user;
    }

    public void setCreated_by_user(String created_by_user) {
        this.created_by_user = created_by_user;
    }


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="experiment_id")
    public Experiment getExperiment() {
        return experiment;
    }

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="created_by_user", referencedColumnName="username")
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

    public Long getExperiment_id() {
        return experiment_id;
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

    public void setExperiment_id(Long experiment_id) {
        this.experiment_id = experiment_id;
    }

    public void setCreated_timestamp(Timestamp created_timestamp) {
        this.created_timestamp = created_timestamp;
    }



}
