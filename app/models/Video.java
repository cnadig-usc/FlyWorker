package models;

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

    private String video_name;

    private Long cohort_id;

    private Cohort cohort;

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

    public Long getCohort_id() {
        return cohort_id;
    }

    public void setCohort_id(Long cohort_id) {
        this.cohort_id = cohort_id;
    }

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="cohort_id", referencedColumnName = "cohort_id")
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
