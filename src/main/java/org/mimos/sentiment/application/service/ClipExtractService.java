package org.mimos.sentiment.application.service;

import org.mimos.sentiment.application.service.dto.ClipExtractDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClipExtract.
 */
public interface ClipExtractService {

    /**
     * Save a clipExtract.
     *
     * @param clipExtractDTO the entity to save
     * @return the persisted entity
     */
    ClipExtractDTO save(ClipExtractDTO clipExtractDTO);

    /**
     * Get all the clipExtracts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClipExtractDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clipExtract.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClipExtractDTO findOne(String id);

    /**
     * Delete the "id" clipExtract.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the clipExtract corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClipExtractDTO> search(String query, Pageable pageable);
}
