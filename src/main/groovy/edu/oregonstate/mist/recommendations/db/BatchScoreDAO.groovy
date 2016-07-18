package edu.oregonstate.mist.recommendations.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlUpdate

/**
 * BatchScore DAO
 */
public interface BatchScoreDAO extends Closeable {

    @SqlUpdate("""
                INSERT INTO BATCH_SCORE (ID, STUDENT_POOL_ID, YEAR, MIN_SCORE)
                SELECT SEQ_BATCH_SCORE.NEXTVAL, STUDENT_POOL.ID, '2012', 510
                FROM STUDENT_POOL
                WHERE PROVINCE = '浙江' AND
                     STU_TYPE = '理科' AND
                     BATCH = 1
               """)
    void insertBatchScore(@Bind("province") String province,
                          @Bind("studentType") String studentType,
                          @Bind("batch") Integer batch,
                          @Bind("year") Integer year,
                          @Bind("minScore") Integer minScore)

    @Override
    void close()
}
