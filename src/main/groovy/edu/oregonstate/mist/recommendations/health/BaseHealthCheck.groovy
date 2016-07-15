package edu.oregonstate.mist.recommendations.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import edu.oregonstate.mist.recommendations.db.StudentPoolDAO

/**
 * Basic Health Check
 */
class BaseHealthCheck extends HealthCheck {

    private final StudentPoolDAO STUDENT_POOL_DAO

    public BaseHealthCheck (StudentPoolDAO studentPoolDAO) {
        this.STUDENT_POOL_DAO = studentPoolDAO
    }

    @Override
    protected Result check () throws Exception {
        try {
            STUDENT_POOL_DAO.getPoolID("福建", "理科", 1)
            Result.healthy()
        } catch (Exception e) {
            Result.unhealthy(e.message)
        }
    }
}
