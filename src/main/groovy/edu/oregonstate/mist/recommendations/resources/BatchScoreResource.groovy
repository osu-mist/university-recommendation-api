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
import javax.ws.rs.DELETE
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * BatchScore Resource
 */
@Path('/batch-scores/')
@Produces(MediaType.APPLICATION_JSON)
class BatchScoreResource extends Resource {
    private final BatchScoreDAO BATCH_SCORE_DAO
    private final StudentPoolDAO STUDENT_POOL_DAO
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchScoreResource.class)

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

            def batchScoreID = BATCH_SCORE_DAO.getBatchScoreID(
                    stuPool.province, stuPool.studentType, stuPool.batch, batchScore.year)
            int batchScoreId = batchScoreID ?: Constants.NOT_FOUND

            if (batchScoreId != Constants.NOT_FOUND) {
                return badRequest(String.format(
                        'Record exists! Please refer to: /batch-scores/%1$d,',batchScoreId))
                        .build()

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
            LOGGER.error("Exception while calling: postBatchScore", e)
            return internalServerError(e.message).build()
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBatchScoreById(@Auth AuthenticatedUser authenticatedUser, @PathParam("id") Integer id) {
        try {
            BATCH_SCORE_DAO.deleteBatchScoreById(id)
            return  ok().build()
        } catch (Exception e) {
            LOGGER.error("Exception while calling: deleteBatchScoreById", e)
            return internalServerError(e.message).build()
        }
    }

}
