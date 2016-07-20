package edu.oregonstate.mist.recommendations.core

import javax.validation.constraints.NotNull

/**
 * Student Pool Class
 */
class StudentPool {
    String province
    String studentType

    @NotNull
    Integer batch
}
