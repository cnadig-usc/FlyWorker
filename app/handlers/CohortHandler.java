package handlers;

import com.avaje.ebean.Ebean;
import constants.AppConstant;
import exceptions.VideoMungerException;
import models.Account;
import models.Cohort;
import models.Experiment;

import javax.persistence.PersistenceException;
import java.util.Map;

/**
 * Created by johntower on 9/4/14.
 */

public class CohortHandler {

    public static Long createCohort(Map<String, String> params) throws VideoMungerException {
        Cohort cohort = new Cohort();
        Account account = null;
        Experiment experiment = null;
        try {

            cohort.setCohort_name(params.get(AppConstant.COHORT_NAME));

            experiment = ExperimentHandler.findExperiment(Long.valueOf(params.get(AppConstant.EXPERIMENT_ID)));

            cohort.setExperiment(experiment);

            account = AccountHandler.findAccount(params.get(AppConstant.CREATED_BY_USER));

            cohort.setCreated_by_account(account);
            cohort.setNumber_of_vials(Integer.valueOf(params.get(AppConstant.NO_OF_VIALS)));
            cohort.setNumber_of_groups(Integer.valueOf(params.get(AppConstant.NO_OF_GROUPS)));

            //Status of the cohort ! might have to edit how this is set.
            cohort.setStatus("CREATED");

            Ebean.save(cohort);
            return cohort.getCohort_id();

        } catch (PersistenceException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new VideoMungerException("Cohort with the name :"+params.get(AppConstant.COHORT_NAME)+" exists.");
            }
        } catch (NullPointerException e) {
            if (account == null) {
                throw new VideoMungerException("Session error. Invalid account: "+params.get(AppConstant.CREATED_BY_USER));
            } else if (experiment == null) {
                throw new VideoMungerException("Experiment not found: "+params.get(AppConstant.EXPERIMENT_ID));
            } else {
                throw new VideoMungerException(e.getMessage());
            }
        } catch (Exception e) {
            throw new VideoMungerException(e.getMessage());
        }
        return null;
    }
}
