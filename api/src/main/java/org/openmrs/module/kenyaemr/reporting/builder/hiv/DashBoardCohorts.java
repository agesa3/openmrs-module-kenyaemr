package org.openmrs.module.kenyaemr.reporting.builder.hiv;

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.CumulativeOnARTCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ETLCurrentOnARTCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ETLCurrentOnCareCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ETLNewOnARTCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731.ETLMoh731CohortLibrary;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.AllPatientsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.library.BuiltInCohortDefinitionLibrary;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by antony on 09/08/17.
 */
public class DashBoardCohorts {

    @Autowired
    private ETLMoh731CohortLibrary moh731Cohorts;

    /**
     * If your use case allows it you should always use #allPatients(EvaluationContext); this method will not cache
     * results, which can significantly impact efficiency.
     *
     * @return All non-test patients in the system (with test patients excluded per the global property
     * {@link org.openmrs.module.reporting.ReportingConstants#GLOBAL_PROPERTY_TEST_PATIENTS_COHORT_DEFINITION}
     */
    public static EvaluatedCohort allPatients() {
        return allPatients(null);
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return All non-test patients in the system (with test patients excluded per the global property
     * {@link org.openmrs.module.reporting.ReportingConstants#GLOBAL_PROPERTY_TEST_PATIENTS_COHORT_DEFINITION}
     */
    public static EvaluatedCohort allPatients(EvaluationContext context) {
        try {
            return getService().evaluate(new AllPatientsCohortDefinition(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating allPatients", e);
        }
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return
     */
    public static EvaluatedCohort males(EvaluationContext context) {
        try {
            return getService().evaluate(new BuiltInCohortDefinitionLibrary().getMales(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating males", e);
        }
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return
     */
    public static EvaluatedCohort females(EvaluationContext context) {
        try {
            return getService().evaluate(new BuiltInCohortDefinitionLibrary().getFemales(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating females", e);
        }
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return
     */
    public static EvaluatedCohort patientsOnART(EvaluationContext context) {
        try {
            return getService().evaluate(new CumulativeOnARTCohortDefinition(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating cumulative on art", e);
        }
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return
     */
    public static EvaluatedCohort onART(EvaluationContext context) {
        try {
            return getService().evaluate(new ETLCurrentOnARTCohortDefinition(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating cumulative on art", e);
        }
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return
     */
    public static EvaluatedCohort inCare(EvaluationContext context) {
        try {
            return getService().evaluate(new ETLCurrentOnCareCohortDefinition(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating current in care", e);
        }
    }

    /**
     * @param context optional (used to return a cached value if possible)
     * @return
     */
    public static EvaluatedCohort newOnART(EvaluationContext context) {
        try {
            return getService().evaluate(new ETLNewOnARTCohortDefinition(), context);
        } catch (EvaluationException e) {
            throw new IllegalStateException("Error evaluating new on art", e);
        }
    }
    private static CohortDefinitionService getService() {
        return Context.getService(CohortDefinitionService.class);
    }
}
