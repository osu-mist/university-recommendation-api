package edu.oregonstate.mist.recommendations.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.db.RecommendationDAO

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Recommendation Resource Class
 */
@Path('/recommendation/')
@Produces(MediaType.APPLICATION_JSON)
class RecommendationResource extends Resource {
    private final RecommendationDAO recommendationDAO

    public RecommendationResource(RecommendationDAO recommendationDAO){
        this.recommendationDAO = recommendationDAO
    }

    @GET
    @Path('rankings')
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recommendation> getRecommendationsByRank(){

    }

}