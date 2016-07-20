package edu.oregonstate.mist.recommendations.core

import javax.validation.constraints.NotNull

/**
 * Batch Score Class
 */
class BatchScore {
    Integer id

    @NotNull
    StudentPool studentPool

    @NotNull
    Integer year

    @NotNull
    Integer minScore
}
