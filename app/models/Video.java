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

    @ManyToOne(fetch= FetchType.EAGER , optional = false)
    private Cohort cohort;

    @CreatedTimestamp
    private Timestamp uploaded_timestap;


    String video_blob;

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

    public Cohort getCohort() {
        return cohort;
    }

    public Timestamp getUploaded_timestap() {
        return uploaded_timestap;
    }

    public void setUploaded_timestap(Timestamp uploaded_timestap) {
        this.uploaded_timestap = uploaded_timestap;
    }

    public String getVideo_blob() {
        return video_blob;
    }

    public void setVideo_blob(String video_blob) {
        this.video_blob = video_blob;
    }
}
