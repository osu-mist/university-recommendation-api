package edu.oregonstate.mist.recommendations.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.db.RecommendationDAO

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
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
    public List<Recommendation> getRecommendationsByRank(
             @QueryParam('stu_type') String studentType,
             @QueryParam('province') String province,
             @QueryParam('batch') Integer batch,
             @QueryParam('rank_min') Integer rankMin,
             @QueryParam('rank_max') Integer rankMax,
             @QueryParam('year') OptionalInt  year,
             @QueryParam('majors')  Optional<String> majors,
             @QueryParam('language') Optional<String> language,
             @QueryParam('page_size') OptionalInt pageSize,
             @QueryParam('page_number') OptionalInt pageNum){
        List<Recommendation> recommendationList = recommendationDAO.getUniversitiesByRank(
                "福建","理科", 1, 5000, 8000, 2015, 10, 0 )
        recommendationList
    }

}