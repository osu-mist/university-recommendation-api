package edu.oregonstate.mist.recommendations.mapper

import edu.oregonstate.mist.recommendations.core.BatchScore
import edu.oregonstate.mist.recommendations.core.StudentPool
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet
import java.sql.SQLException

/**
 * BatchScoreMapper
 */
class BatchScoreMapper implements ResultSetMapper<BatchScore>{

    @Override
    BatchScore map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        new BatchScore(
                id: r.getInt('ID'),
                year: r.getInt('YEAR'),
                minScore: r.getInt('MIN_SCORE')
        )
    }
}
