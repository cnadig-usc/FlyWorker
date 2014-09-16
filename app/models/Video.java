package models;

import com.avaje.ebean.annotation.CreatedTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by johntower on 9/4/14.
 */

@Entity
@Table(name="video")
public class Video {

    @Id
    private Long video_id;

    @Column(nullable = false)
    private String video_name;

    @Column(nullable=false)
    private String group_name;

    @Column(nullable=false)
    private Long camera_id;

    @Column(nullable=false)
    private String video_file_path;

    @ManyToOne(fetch= FetchType.EAGER , optional = false)
    private Cohort cohort;

    @ManyToOne(fetch= FetchType.EAGER, optional = false)
    private Account uploaded_by_account;

    @CreatedTimestamp
    private Timestamp uploaded_timestap;

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Long getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(Long camera_id) {
        this.camera_id = camera_id;
    }

    public String getVideo_file_path() {
        return video_file_path;
    }

    public void setVideo_file_path(String video_file_path) {
        this.video_file_path = video_file_path;
    }

    public Cohort getCohort() {
        return cohort;
    }

    public void setCohort(Cohort cohort) {
        this.cohort = cohort;
    }

    public Account getUploaded_by_account() {
        return uploaded_by_account;
    }

    public void setUploaded_by_account(Account uploaded_by_account) {
        this.uploaded_by_account = uploaded_by_account;
    }

    public Timestamp getUploaded_timestap() {
        return uploaded_timestap;
    }

    public void setUploaded_timestap(Timestamp uploaded_timestap) {
        this.uploaded_timestap = uploaded_timestap;
    }
}
