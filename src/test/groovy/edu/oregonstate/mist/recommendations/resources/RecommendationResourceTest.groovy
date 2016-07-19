package edu.oregonstate.mist.recommendations.resources

import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test

import static junit.framework.TestCase.assertEquals
import static junit.framework.TestCase.assertNull
import static org.mockito.Mockito.*

/**
 * RecommendationResource Test
 */
public class RecommendationResourceTest {

    private static final RecommendationDAO RECOMMENDATION_DAO = mock(RecommendationDAO.class)
//    private final List<Recommendation> recommendationList

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new RecommendationResource(RECOMMENDATION_DAO))
            .build()

    @Before
    public void setup () {

    }

    @Test
    public void getRecommendations () throws Exception {
        // resources.client().target("/recommendations?").queryParam("by","ranking")
        //         .queryParam("batch", 1).queryParam("province","fujian")
        //         .queryParam("student_type","science").queryParam("lower_limit", 0)
        //         .queryParam("upper_limit",3000).request().get()
    }

    @Test
    public void translationTest() throws Exception {
        Resource.loadProperties("provinces.properties")
        Resource.loadProperties('universities.properties')

        assertNull(RecommendationResource.translate(""))
        assertNull(RecommendationResource.translate(""))

        assertNull(RecommendationResource.translate("zhejiang"))
        assertNull(RecommendationResource.translate("工商大学"))

        assertEquals('福建', RecommendationResource.translate("fujian"))
        assertEquals('Tsinghua University', RecommendationResource.translate("清华大学"))
        assertEquals('South China University of Technology', RecommendationResource.translate("南方科技大学"))

    }

    @After
    public void tearDown () {
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(RECOMMENDATION_DAO)
    }

}