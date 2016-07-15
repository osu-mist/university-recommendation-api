package edu.oregonstate.mist.recommendations

import edu.oregonstate.mist.api.Configuration
import io.dropwizard.db.DataSourceFactory

public class RecommendationConfiguration extends Configuration {
    DataSourceFactory database = new DataSourceFactory()
}
