package edu.oregonstate.mist.recommendations.resources

import edu.oregonstate.mist.recommendations.db.RecommendationDAO
import org.junit.Test

import static org.mockito.Mockito.mock

/**
 * RecommendationResource Test
 */
public class RecommendationResourceTest {

    private static final RecommendationDAO RECOMMENDATION_DAO = mock(RecommendationDAO.class);

    @Test
    public void getRecommendationsByRank() throws Exception {

    }

}