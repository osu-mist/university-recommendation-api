package edu.oregonstate.mist.recommendations.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery

/**
 * StudentPool DAO
 */

public interface StudentPoolDAO extends Closeable {

    @SqlQuery("""
                SELECT ID
                FROM STUDENT_POOL
                WHERE STU_TYPE = :studentType AND
                      PROVINCE = :province AND
                      BATCH = :batch
                """)
    Integer getPoolID (@Bind("province") String province,
                       @Bind("studentType") String studentType,
                       @Bind("batch") Integer batch)

    @Override
    void close()
}