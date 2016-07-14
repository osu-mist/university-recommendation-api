package edu.oregonstate.mist.recommendations.mapper

import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.core.University
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException
/**
 * Recommendation Mapper
 */
public class RecommendationMapper implements ResultSetMapper<Recommendation>{

    @Override
    Recommendation map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        new Recommendation(
                id:            r.getInt('R_NUM'),
                university:    new University(
                            name:   r.getNString('UNIVERSITY'),
                            is985:  r.getInt('IS_985'),
                            is211:  r.getInt('IS_211'),
                         ),
                major:         r.getNString('MAJOR'),
                year:          r.getInt('YEAR'),
                studentCount:  r.getInt('STU_COUNT'),
                scoreAvg:      r.getInt('SCORE_AVG'),
                rank:          r.getInt('RANK'),
                scoreBatch:    r.getInt('SCORE_BATCH'),
                scoreDiff:     r.getInt('SCORE_DIFF'),
        )
    }
}
