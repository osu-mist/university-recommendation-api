package edu.oregonstate.mist.recommendations.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
/**
 * Students Pool DAO
 */
//@RegisterMapper()
public interface StudentPoolDAO extends Cloneable{
    /**
     *
     * @param studentType
     * @param province
     * @param batch
     * @return Integer standing for student pool id
     */
    @SqlQuery("""
               SELECT ID FROM STUDENT_POOL
               WHERE STU_TYPE =: studentType and
               PROVINCE =: province and
               BATCH =: batch
              """)
    Integer getPoolId(@Bind("studentType") String studentType,
                      @Bind("province") String province,
                      @Bind("batch") Integer batch
                      )
    
}
