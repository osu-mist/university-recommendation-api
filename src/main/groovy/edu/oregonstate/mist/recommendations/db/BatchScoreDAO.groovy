package edu.oregonstate.mist.recommendations.db

/**
 * BatchScore DAO
 */
public interface BatchScoreDAO extends Closeable {
    @Override
    void close()
}
