package handlers;

import com.avaje.ebean.Ebean;
import models.Experiment;

/**
 * Created by johntower on 9/4/14.
 */
public class ExperimentHandler {

    public static Experiment findExperiment (Long experiment_id) {

        Experiment experiment = Ebean.find(Experiment.class)
                .where()
                .eq("experiment_id", experiment_id)
                .findUnique();
       return experiment;

    }

}
