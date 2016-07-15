package edu.oregonstate.mist.recommendations.resources

import com.google.common.base.Optional
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import org.hibernate.validator.constraints.NotEmpty

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

/**
 * Recommendation Resource Class
 */
@Path('/recommendations/')
@Produces(MediaType.APPLICATION_JSON)
class RecommendationResource extends Resource {
    private final RecommendationDAO recommendationDAO

    public RecommendationResource (RecommendationDAO recommendationDAO) {
        this.recommendationDAO = recommendationDAO
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recommendation> getRecommendationsByRank (
            @QueryParam('by') @NotEmpty String by,
            @QueryParam('stu_type') @NotEmpty String studentType,
            @QueryParam('province') @NotEmpty String province,
            @QueryParam('batch') @NotEmpty Integer batch,
            @QueryParam('lower_limit') @NotEmpty Integer lowerLimit,
            @QueryParam('upper_limit') @NotEmpty Integer upperLimit,
            @QueryParam('year') Optional<Integer>  year,
            @QueryParam('major')  Optional<String> major,
            @QueryParam('language') Optional<String> language,
            @QueryParam('page_size') Optional<Integer> pageSize,
            @QueryParam('page_number') Optional<Integer> pageNum){

            List<Recommendation> recommendationList
            if (by.toLowerCase () == "ranking") {
                if (major.isPresent ()) {
//                  recommendationList = RecommendationDAO.getMajorsByRank()
                }else {
                    recommendationList = recommendationDAO.getUniversitiesByRank(
                            "福建","理科", 1, 5000, 8000, 2015, 10, 0 )
                }
            }else if (bytoLowerCase() == "score-diff") {
                // TO-DO
            }

            recommendationList
    }

    /**
     * Translate Student Type into Chinese if needed
     * @param stuType
     * @return Student Type In Chinese
     */
    private String translateStuType (String stuType) {
        if (stuType.toLowerCase () == "arts") {
            stuType = "文科"
        }else if (stuType.toCharArray () == "science") {
            stuType = "理科"
        }
        stuType
    }

    /**
     * Translate Province parameter into Chinese if needed
     * @param province
     * @return
     */
    private String translateProvince (String province) {
        province = "福建"
        province
    }
}