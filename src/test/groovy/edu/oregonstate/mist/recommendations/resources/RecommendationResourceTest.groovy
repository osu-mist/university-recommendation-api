package edu.oregonstate.mist.recommendations.resources

import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test

import static org.mockito.Mockito.*;

/**
 * RecommendationResource Test
 */
public class RecommendationResourceTest {

//    private static final RecommendationDAO RECOMMENDATION_DAO = mock(RecommendationDAO.class);
//    private final List<Recommendation> recommendationList;
//
//    @ClassRule
//    public static final ResourceTestRule resources = ResourceTestRule.builder()
//            .addResource(new RecommendationResource(RECOMMENDATION_DAO))
//            .build();
//
//    @Before
//    public void setup () {
////        when(RECOMMENDATION_DAO.getUniversitiesByRank("理科", "福建", 1, 5000, 10000,2015, 10, 1)).thenReturn(recommendationList)
//    }
//
//    @Test
//    public void getRecommendations () throws Exception {
////        resources.client().target("/recommendations?" +
////                "by=ranking&&stu_type=science&&province=fujian&&batch=1&lower_limit=5000&upper_limit=8000&&year=2015").request();
//    }
//
//    @After
//    public void tearDown (){
//        // we have to reset the mock after each test because of the
//        // @ClassRule, or use a @Rule as mentioned below.
//        reset(RECOMMENDATION_DAO);
//    }

}