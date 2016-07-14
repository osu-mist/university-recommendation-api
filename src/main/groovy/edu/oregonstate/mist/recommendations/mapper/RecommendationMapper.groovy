package edu.oregonstate.mist.recommendations.mapper

import edu.oregonstate.mist.recommendations.core.Recommendation
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException
/**
 * Recommendation Mapper
 */
public class RecommendationMapper implements ResultSetMapper<Recommendation>{

    /**
     * Map the row the result set is at when passed in. This method should not cause the result
     * set to advance, allow jDBI to do that, please.
     *
     * @param index which row of the result set we are at, starts at 0
     * @param r     the result set being iterated
     * @param ctx
     * @return the value to return for this row
     * @throws SQLException if anythign goes wrong go ahead and let this percolate, jDBI will handle it
     */
    @Override
    Recommendation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        new Recommendation(
                id:            r.getInt('R_NUM'),
                university.id: r.getInt('UNIVERSITY_ID'),
                major:         r.getString('MAJOR'),
                year:          r.getInt('YEAR'),
                studentCount:  r.getInt('STU_COUNT'),
                scoreAvg:      r.getInt('SCORE_AVG'),
                rank:          r.getInt('RANK'),
                scoreBatch:    r.getInt('SCORE_BATCH'),
                scoreDiff:     r.getInt('SCORE_DIFF'),
        )
    }
}
