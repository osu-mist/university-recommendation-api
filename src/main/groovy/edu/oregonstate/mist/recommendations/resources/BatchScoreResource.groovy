package edu.oregonstate.mist.recommendations.resources

import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.recommendations.Constants
import edu.oregonstate.mist.recommendations.core.BatchScore
import edu.oregonstate.mist.recommendations.core.StudentPool
import edu.oregonstate.mist.recommendations.db.BatchScoreDAO
import edu.oregonstate.mist.recommendations.db.StudentPoolDAO
import io.dropwizard.auth.Auth

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * BatchScore Resource
 */
@Path('/batch-scores/')
@Produces(MediaType.APPLICATION_JSON)
class BatchScoreResource extends Resource {
    private final BatchScoreDAO BATCH_SCORE_DAO
    private final StudentPoolDAO STUDENT_POOL_DAO

    public BatchScoreResource (BatchScoreDAO batchScoreDAO, StudentPoolDAO studentPoolDAO) {
        this.BATCH_SCORE_DAO = batchScoreDAO
        this.STUDENT_POOL_DAO = studentPoolDAO
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBatchScore(@Auth AuthenticatedUser authenticatedUser, @Valid BatchScore batchScore) {
        try {
            StudentPool stuPool = batchScore.studentPool
            int batchScoreId = BATCH_SCORE_DAO.getBatchScoreID (stuPool.province,
                                                                stuPool.studentType,
                                                                stuPool.batch,
                                                                batchScore.year) ?: Constants.NOT_FOUND
            if (batchScoreId != Constants.NOT_FOUND) {
                return badRequest(String.format(
                        'Record existed! Please use PUT to update it, Record Id: %1$d',batchScoreId)).build()

            } else {
                    STUDENT_POOL_DAO.insertStudentPoolIfNotExisted (stuPool.province,
                            stuPool.studentType,
                            stuPool.batch)
                    BATCH_SCORE_DAO.insertBatchScore (stuPool.province,
                                      stuPool.studentType,
                                      stuPool.batch,
                                      batchScore.year,
                                      batchScore.minScore)
                    batchScore.id = BATCH_SCORE_DAO.getBatchScoreID (stuPool.province,
                                                                     stuPool.studentType,
                                                                     stuPool.batch,
                                                                     batchScore.year)

                    return ok(batchScore).build()
            }
        } catch (Exception e) {
            return internalServerError(e.message).build()
        }
    }
}
