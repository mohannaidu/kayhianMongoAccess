package org.mimos.sentiment.application.repository;

import org.mimos.sentiment.application.domain.ClipExtract;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ClipExtract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClipExtractRepository extends MongoRepository<ClipExtract, String> {

}
