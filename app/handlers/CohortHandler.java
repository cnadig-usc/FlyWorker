package handlers;

import com.avaje.ebean.Ebean;
import constants.AppConstant;
import models.Cohort;

import java.util.Map;

/**
 * Created by johntower on 9/4/14.
 */

public class CohortHandler {

    public static void createCohort(Map<String, String> params) {
        Cohort cohort = new Cohort();

        System.out.println(ExperimentHandler.findExperiment(Long.valueOf(params.get(AppConstant.EXPERIMENT_ID))).getExperiment_id());

        cohort.setCohort_name(params.get(AppConstant.COHORT_NAME));

        cohort.setExperiment(ExperimentHandler.findExperiment(Long.valueOf(params.get(AppConstant.EXPERIMENT_ID))));


//        cohort.setExperiment_id(Long.valueOf(params.get(AppConstant.EXPERIMENT_ID)));
        cohort.setCreated_by_user(params.get(AppConstant.CREATED_BY_USER));
        cohort.setNumber_of_vials(Integer.valueOf(params.get(AppConstant.NO_OF_VIALS)));
        cohort.setNumber_of_groups(Integer.valueOf(params.get(AppConstant.NO_OF_GROUPS)));

        Ebean.save(cohort);


    }
}
