package org.mimos.sentiment.application.service.impl;

import org.mimos.sentiment.application.service.ClipExtractService;
import org.mimos.sentiment.application.domain.ClipExtract;
import org.mimos.sentiment.application.repository.ClipExtractRepository;
import org.mimos.sentiment.application.repository.search.ClipExtractSearchRepository;
import org.mimos.sentiment.application.service.dto.ClipExtractDTO;
import org.mimos.sentiment.application.service.mapper.ClipExtractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ClipExtract.
 */
@Service
public class ClipExtractServiceImpl implements ClipExtractService {

    private final Logger log = LoggerFactory.getLogger(ClipExtractServiceImpl.class);

    private final ClipExtractRepository clipExtractRepository;

    private final ClipExtractMapper clipExtractMapper;

    private final ClipExtractSearchRepository clipExtractSearchRepository;

    public ClipExtractServiceImpl(ClipExtractRepository clipExtractRepository, ClipExtractMapper clipExtractMapper, ClipExtractSearchRepository clipExtractSearchRepository) {
        this.clipExtractRepository = clipExtractRepository;
        this.clipExtractMapper = clipExtractMapper;
        this.clipExtractSearchRepository = clipExtractSearchRepository;
    }

    /**
     * Save a clipExtract.
     *
     * @param clipExtractDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClipExtractDTO save(ClipExtractDTO clipExtractDTO) {
        log.debug("Request to save ClipExtract : {}", clipExtractDTO);
        ClipExtract clipExtract = clipExtractMapper.toEntity(clipExtractDTO);
        clipExtract = clipExtractRepository.save(clipExtract);
        ClipExtractDTO result = clipExtractMapper.toDto(clipExtract);
        clipExtractSearchRepository.save(clipExtract);
        return result;
    }

    /**
     * Get all the clipExtracts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ClipExtractDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClipExtracts");
        return clipExtractRepository.findAll(pageable)
            .map(clipExtractMapper::toDto);
    }

    /**
     * Get one clipExtract by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public ClipExtractDTO findOne(String id) {
        log.debug("Request to get ClipExtract : {}", id);
        ClipExtract clipExtract = clipExtractRepository.findOne(id);
        return clipExtractMapper.toDto(clipExtract);
    }

    /**
     * Delete the clipExtract by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ClipExtract : {}", id);
        clipExtractRepository.delete(id);
        clipExtractSearchRepository.delete(id);
    }

    /**
     * Search for the clipExtract corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ClipExtractDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClipExtracts for query {}", query);
        Page<ClipExtract> result = clipExtractSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(clipExtractMapper::toDto);
    }
}
