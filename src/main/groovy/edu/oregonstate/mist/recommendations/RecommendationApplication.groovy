package edu.oregonstate.mist.recommendations

import com.codahale.metrics.MetricRegistry
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.BasicAuthenticator
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import edu.oregonstate.mist.recommendations.db.StudentPoolDAO
import edu.oregonstate.mist.recommendations.health.BaseHealthCheck
import edu.oregonstate.mist.recommendations.resources.RecommendationResource
import io.dropwizard.Application
import io.dropwizard.auth.AuthFactory
import io.dropwizard.auth.basic.BasicAuthFactory
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

class RecommendationApplication extends Application<RecommendationConfiguration> {
    /**
     * When the application runs, this is called after the {@link Bundle}s are run. Override it to add
     * providers, resources, etc. for your application.
     *
     * @param configuration the parsed {@link Configuration} object
     * @param environment the application's {@link Environment}
     * @throws Exception if something goes wrong
     */
    @Override
    void run(RecommendationConfiguration configuration, Environment environment) throws Exception {
        Resource.loadProperties('resource.properties')
        Resource.loadProperties('provinces.properties')
        Resource.loadProperties('universities.properties')
        final DBIFactory FACTORY = new DBIFactory()
        final DBI JDBI = FACTORY.build(environment, configuration.getDatabase(), "JDBI")

        final RecommendationDAO RECOMMENDATION_DAO = JDBI.onDemand(RecommendationDAO.class)
        final StudentPoolDAO STUDENT_POOL_DAO = JDBI.onDemand(StudentPoolDAO.class)

        environment.jersey().register(new RecommendationResource(RECOMMENDATION_DAO))

        environment.jersey().register(
                AuthFactory.binder(
                        new BasicAuthFactory<AuthenticatedUser>(
                                new BasicAuthenticator(configuration.getCredentialsList()),
                                'RecommendationApplication',
                                AuthenticatedUser.class)))
        //health check
        environment.healthChecks()register("basic health check", new BaseHealthCheck((STUDENT_POOL_DAO)))
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new RecommendationApplication().run(arguments)
    }
}
