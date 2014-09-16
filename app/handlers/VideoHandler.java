package handlers;

import com.avaje.ebean.Ebean;
import constants.AppConstant;
import exceptions.VideoMungerException;
import models.Account;
import models.Cohort;
import models.Video;

import javax.persistence.PersistenceException;
import java.util.Map;

/**
 * Created by johntower on 9/16/14.
 */
public class VideoHandler {

    public static Long uploadVideo (Map<String, String> params) throws VideoMungerException {

        Cohort cohort = null;
        Account account = null;
        Video video = new Video();

        try {
            video.setVideo_name(params.get(AppConstant.VIDEO_NAME));
            video.setGroup_name(params.get(AppConstant.GROUP));
            video.setCamera_id(Long.valueOf(params.get(AppConstant.CAMERA_ID)));
            video.setVideo_file_path(params.get(AppConstant.VIDEO_FILE_PATH));

            cohort = CohortHandler.findCohort(Long.valueOf(params.get(AppConstant.COHORT_ID)));
            account = AccountHandler.findAccount(params.get(AppConstant.UPLOADED_BY_USER));

            video.setCohort(cohort);
            video.setUploaded_by_account(account);

            Ebean.save(video);
            return video.getVideo_id();

        } catch (PersistenceException e) {
            throw new VideoMungerException("Something went wrong updating video table: "+e.getMessage());
        }
        catch (NullPointerException e) {
            throw new VideoMungerException("Couldn't find either account or cohort: "+e.getMessage());
        } catch (Exception e) {
            throw new VideoMungerException(e.getMessage());
        }
    }
}
