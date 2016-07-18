package edu.oregonstate.mist.recommendations

import org.omg.CORBA.PUBLIC_MEMBER

/**
 *  Recommendation API Constants
 */
class Constants {
    public static final String BY_RANKING = "RANKING"
    public static final String BY_SCORE_DIFF = "SCORE-DIFF"

    public static final enum STUDENT_TYPE {
        ARTS,
        SCIENCE
    }

    public static final String ARTS_IN_CHINESE = "文科"
    public static final String SCIENCE_IN_CHINESE = "理科"

    public static final enum LANGUAGE {
        EN,
        CN
    }

}
