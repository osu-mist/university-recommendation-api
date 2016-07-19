package edu.oregonstate.mist.recommendations.resources

import com.codahale.metrics.annotation.Metered
import com.codahale.metrics.annotation.Timed
import com.google.common.base.Optional
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.Constants
import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Recommendation Resource Class
 */
@Path('/recommendations/')
@Produces(MediaType.APPLICATION_JSON)
class RecommendationResource extends Resource {
    private final RecommendationDAO recommendationDAO
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationResource.class)

    public RecommendationResource(RecommendationDAO recommendationDAO) {
        this.recommendationDAO = recommendationDAO
    }

    @GET
    @Timed(name = "timedGetRecommendations")
    @Metered(name = "meterGetRecommendations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendations(
            @QueryParam('by') String by,
            @QueryParam('student_type') String studentType,
            @QueryParam('province') String province,
            @QueryParam('batch') Integer batch,
            @QueryParam('lower_limit') Integer lowerLimit,
            @QueryParam('upper_limit') Integer upperLimit,
            @QueryParam('year') Optional<Integer> year,
            @QueryParam('major') Optional<String> major,
            @QueryParam('language') Optional<String> language,
            @QueryParam('page_size') Optional<Integer> pageSize,
            @QueryParam('page_number') Optional<Integer> pageNum) {

        if (!(by && studentType && province && batch && upperLimit && lowerLimit != null)) {
            return badRequest("Some of the required query parameters are not provided!").build()
        }

        if (batch > 3 || batch < 1) {
            return badRequest("Batch should be between 1 and 3")
        }

        if (lowerLimit > upperLimit || upperLimit <= 0) {
            return badRequest("LowerLimit should be less than upperLimit and upperLimit should be greater than 0")
        }

        List<Recommendation> recommendationList

        try {
            recommendationList = doGetRecommendations (
                                    by, studentType, province, batch,
                                    lowerLimit, upperLimit, year,
                                    major, language, pageSize, pageNum)
        } catch (Exception e) {
            LOGGER.error("Exception when calling: getRecommendations", e)
            return internalServerError(e.message).build()
        }

        if (recommendationList && recommendationList.size () > 0) {
            return ok(recommendationList).build()
        }else{
            return notFound().build()
        }

    }

    /**
     * Query for recommendations based on query parameters
     * @param by
     * @param studentType
     * @param province
     * @param batch
     * @param lowerLimit
     * @param upperLimit
     * @param year
     * @param major
     * @param language
     * @param pageSize
     * @param pageNum
     * @return A list of recommendations
     * @throws Exception
     */
    private List<Recommendation> doGetRecommendations (String by,
                                                       String studentType,
                                                       String province,
                                                       Integer batch,
                                                       Integer lowerLimit,
                                                       Integer upperLimit,
                                                       Optional<Integer> year,
                                                       Optional<String> major,
                                                       Optional<String> language,
                                                       Optional<Integer> pageSize,
                                                       Optional<Integer> pageNum) throws Exception{
        studentType = translateStuType(studentType)
        province = translate(province)?: province
        List<Recommendation> recommendationList
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
        } else if (by.toUpperCase () == Constants.BY_SCORE_DIFF) {
            // TO-DO
            recommendationList = []
        } else {
            throw new Exception("Parameter 'by' is not matched!")
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
    private static List<Recommendation> translateResult (List<Recommendation> recommendationList) {
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
    private static String translate(String sourceText) {
        String translated = Resource.properties.get(sourceText.trim())
        if (translated) {
            translated = translated.trim()
        }
        translated
    }
}