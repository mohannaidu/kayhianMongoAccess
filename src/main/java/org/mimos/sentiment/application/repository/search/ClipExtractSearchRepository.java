package org.mimos.sentiment.application.repository.search;

import org.mimos.sentiment.application.domain.ClipExtract;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClipExtract entity.
 */
public interface ClipExtractSearchRepository extends ElasticsearchRepository<ClipExtract, String> {
}
