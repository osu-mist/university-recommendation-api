package edu.oregonstate.mist.recommendations.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate

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

    @SqlUpdate("""
                INSERT INTO STUDENT_POOL (ID, PROVINCE, BATCH, STU_TYPE)
                    SELECT SEQ_STU_POOL.NEXTVAL, :province , :batch, :studentType
                    FROM DUAL
                    WHERE NOT EXISTS (
                      SELECT * FROM STUDENT_POOL
                      WHERE PROVINCE = :province AND
                            BATCH = :batch AND
                            STU_TYPE = :studentType
                    )
               """)
    void insertStudentPoolIfNotExisted (@Bind("province") String province,
                                        @Bind("studentType") String studentType,
                                        @Bind("batch") Integer batch)

    @Override
    void close ()
}