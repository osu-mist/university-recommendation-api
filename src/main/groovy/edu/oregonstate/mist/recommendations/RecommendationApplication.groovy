package edu.oregonstate.mist.recommendations

import edu.oregonstate.mist.api.Resource
import io.dropwizard.setup.Environment

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
        
    }
}
