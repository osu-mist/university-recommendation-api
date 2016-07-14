package edu.oregonstate.mist.recommendations

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import edu.oregonstate.mist.recommendations.resources.RecommendationResource
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

class RecommendationApplication extends io.dropwizard.Application<RecommendationConfiguration> {
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
        final DBIFactory factory = new DBIFactory()
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "jdbi")

        final RecommendationDAO recommendationDAO = jdbi.onDemand(RecommendationDAO.class)

        environment.jersey().register(new RecommendationResource(recommendationDAO))

        //health check
    }


}
