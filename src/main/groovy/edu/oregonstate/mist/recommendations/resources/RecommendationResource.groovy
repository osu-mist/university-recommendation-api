package edu.oregonstate.mist.recommendations.resources

import com.google.common.base.Optional
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.Constants
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
        province = translate(province)?: province
        if (by.toUpperCase() == Constants.BY_RANKING) {
            if (major.isPresent()) {
                recommendationList = recommendationDAO.getMajorsByRank(
                        province, studentType, batch, lowerLimit, upperLimit,
                        year.orNull(), major.orNull(), pageSize.or(10), pageNum.or(1) - 1)

            } else {
                recommendationList = recommendationDAO.getUniversitiesByRank(
                        province, studentType, batch, lowerLimit, upperLimit,
                        year.orNull(), pageSize.or(10), pageNum.or(1) - 1)

            }
        } else if (by.toUpperCase() == Constants.BY_SCORE_DIFF) {
            // TO-DO
            recommendationList = []
        }else{

        }

        if (language.isPresent() && language.get().toUpperCase() == Constants.LANGUAGE.EN.name()) {
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
        if (stuType.toUpperCase() == Constants.STUDENT_TYPE.ARTS.name()) {
            stuType = Constants.ARTS_IN_CHINESE
        } else if (stuType.toUpperCase() == Constants.STUDENT_TYPE.SCIENCE.name()) {
            stuType = Constants.SCIENCE_IN_CHINESE
        }
        stuType
    }

    /**
     *  Translate result into English
     * @param recommendationList
     * @return recommendationList
     */
    private List<Recommendation> translateResult (List<Recommendation> recommendationList) {

        recommendationList.each {
            it.university.name = translate(it.university.name)?: it.university.name
        }
        recommendationList
    }

    /**
     * Translate English to Chinese
     * @param sourceText
     * @return
     */
    private String translate(String sourceText) {
        String translated = Resource.properties.get(sourceText.trim())
        if (translated != null) {
            translated = translated.trim()
        }
        translated
    }
}