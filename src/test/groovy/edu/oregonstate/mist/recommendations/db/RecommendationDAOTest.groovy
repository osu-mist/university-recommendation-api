package edu.oregonstate.mist.recommendations.db

import edu.oregonstate.mist.recommendations.core.Recommendation
/**
 * RecommendationDAO Test
 */
class RecommendationDAOTest extends groovy.util.GroovyTestCase {
    private static RecommendationDAO recommendationDAO

//    @ClassRule
//    public static final DropwizardAppRule<RecommendationConfiguration> APPLICATION =
//            new DropwizardAppRule<RecommendationConfiguration>(
//                    RecommendationApplication.class,
//                    new File("configuration.yaml").absolutePath
//            )

    void setUp() {
        super.setUp()

    }

    void testGetUniversitiesByRank() {
        List<Recommendation> recommendationList = recommendationDAO.getUniversitiesByRank(
                "福建","理科", 1, 5000, 8000, 2015, 10, 0 )
    }

    void testGetMajorsByRank() {

    }
}
