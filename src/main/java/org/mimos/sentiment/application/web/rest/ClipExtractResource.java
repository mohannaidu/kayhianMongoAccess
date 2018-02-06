package org.mimos.sentiment.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mimos.sentiment.application.service.ClipExtractService;
import org.mimos.sentiment.application.web.rest.errors.BadRequestAlertException;
import org.mimos.sentiment.application.web.rest.util.HeaderUtil;
import org.mimos.sentiment.application.web.rest.util.PaginationUtil;
import org.mimos.sentiment.application.service.dto.ClipExtractDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ClipExtract.
 */
@RestController
@RequestMapping("/api")
public class ClipExtractResource {

    private final Logger log = LoggerFactory.getLogger(ClipExtractResource.class);

    private static final String ENTITY_NAME = "clipExtract";

    private final ClipExtractService clipExtractService;

    public ClipExtractResource(ClipExtractService clipExtractService) {
        this.clipExtractService = clipExtractService;
    }

    /**
     * POST  /clip-extracts : Create a new clipExtract.
     *
     * @param clipExtractDTO the clipExtractDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clipExtractDTO, or with status 400 (Bad Request) if the clipExtract has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clip-extracts")
    @Timed
    public ResponseEntity<ClipExtractDTO> createClipExtract(@RequestBody ClipExtractDTO clipExtractDTO) throws URISyntaxException {
        log.debug("REST request to save ClipExtract : {}", clipExtractDTO);
        if (clipExtractDTO.getId() != null) {
            throw new BadRequestAlertException("A new clipExtract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClipExtractDTO result = clipExtractService.save(clipExtractDTO);
        return ResponseEntity.created(new URI("/api/clip-extracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clip-extracts : Updates an existing clipExtract.
     *
     * @param clipExtractDTO the clipExtractDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clipExtractDTO,
     * or with status 400 (Bad Request) if the clipExtractDTO is not valid,
     * or with status 500 (Internal Server Error) if the clipExtractDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clip-extracts")
    @Timed
    public ResponseEntity<ClipExtractDTO> updateClipExtract(@RequestBody ClipExtractDTO clipExtractDTO) throws URISyntaxException {
        log.debug("REST request to update ClipExtract : {}", clipExtractDTO);
        if (clipExtractDTO.getId() == null) {
            return createClipExtract(clipExtractDTO);
        }
        ClipExtractDTO result = clipExtractService.save(clipExtractDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clipExtractDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clip-extracts : get all the clipExtracts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clipExtracts in body
     */
    @GetMapping("/clip-extracts")
    @Timed
    public ResponseEntity<List<ClipExtractDTO>> getAllClipExtracts(Pageable pageable) {
        log.debug("REST request to get a page of ClipExtracts");
        Page<ClipExtractDTO> page = clipExtractService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clip-extracts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clip-extracts/:id : get the "id" clipExtract.
     *
     * @param id the id of the clipExtractDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clipExtractDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clip-extracts/{id}")
    @Timed
    public ResponseEntity<ClipExtractDTO> getClipExtract(@PathVariable String id) {
        log.debug("REST request to get ClipExtract : {}", id);
        ClipExtractDTO clipExtractDTO = clipExtractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clipExtractDTO));
    }

    /**
     * DELETE  /clip-extracts/:id : delete the "id" clipExtract.
     *
     * @param id the id of the clipExtractDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clip-extracts/{id}")
    @Timed
    public ResponseEntity<Void> deleteClipExtract(@PathVariable String id) {
        log.debug("REST request to delete ClipExtract : {}", id);
        clipExtractService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/clip-extracts?query=:query : search for the clipExtract corresponding
     * to the query.
     *
     * @param query the query of the clipExtract search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clip-extracts")
    @Timed
    public ResponseEntity<List<ClipExtractDTO>> searchClipExtracts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ClipExtracts for query {}", query);
        Page<ClipExtractDTO> page = clipExtractService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clip-extracts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
