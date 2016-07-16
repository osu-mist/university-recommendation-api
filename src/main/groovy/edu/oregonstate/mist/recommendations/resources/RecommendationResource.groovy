package edu.oregonstate.mist.recommendations.resources

import com.google.common.base.Optional
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.core.University
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

    public RecommendationResource(RecommendationDAO recommendationDAO) {
        this.recommendationDAO = recommendationDAO
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recommendation> getRecommendations(
            @QueryParam('by') @NotEmpty String by,
            @QueryParam('student_type') @NotEmpty String studentType,
            @QueryParam('province') @NotEmpty String province,
            @QueryParam('batch') @NotEmpty Integer batch,
            @QueryParam('lower_limit') @NotEmpty Integer lowerLimit,
            @QueryParam('upper_limit') @NotEmpty Integer upperLimit,
            @QueryParam('year') Optional<Integer> year,
            @QueryParam('major') Optional<String> major,
            @QueryParam('language') Optional<String> language,
            @QueryParam('page_size') Optional<Integer> pageSize,
            @QueryParam('page_number') Optional<Integer> pageNum) {

        List<Recommendation> recommendationList
        studentType = translateStuType(studentType)
        province = translateProvince(province)
        if (by.toLowerCase() == "ranking") {
            if (major.isPresent()) {
                recommendationList = recommendationDAO.getMajorsByRank(
                        province, studentType, batch, lowerLimit, upperLimit,
                        year.orNull(), major.orNull(), pageSize.or(10), pageNum.or(1) - 1)

            } else {
                recommendationList = recommendationDAO.getUniversitiesByRank(
                        province, studentType, batch, lowerLimit, upperLimit,
                        year.orNull(), pageSize.or(10), pageNum.or(1) - 1)

            }
        } else if (by.toLowerCase() == "score-diff") {
            // TO-DO
            recommendationList = []
        }
        if (language.isPresent() && language.get() == "EN") {
            recommendationList = translateResult (recommendationList)
        }
        recommendationList

    }

    /**
     * Translate Student Type into Chinese if needed
     * @param stuType
     * @return Student Type In Chinese
     */
    private String translateStuType (String stuType) {
        if (stuType.toLowerCase() == "arts") {
            stuType = "文科"
        } else if (stuType.toLowerCase() == "science") {
            stuType = "理科"
        }
        stuType
    }

    /**
     * Translate Province parameter into Chinese if needed
     * @param province
     * @return
     */
    private String translateProvince (String province)
    {
        String translate = Resource.properties.get(province)
        if (translate != null && translate.length() > 0) {
            translate
        } else {
            province
        }
    }

    /**
     *  Tranlate result into English
     * @param recommendationList
     * @return recommendationList
     */
    private List<Recommendation> translateResult (List<Recommendation> recommendationList) {

        for (int i = 0; i < recommendationList.size(); i ++ ){
            String universityName = recommendationList[i].getUniversity().name
            String translate =  Resource.properties.get(universityName)
            if (translate != null){
                University university = recommendationList[i].getUniversity()
                university.setName(translate)
                recommendationList[i].setUniversity(university)
            }
        }
        recommendationList
    }
}