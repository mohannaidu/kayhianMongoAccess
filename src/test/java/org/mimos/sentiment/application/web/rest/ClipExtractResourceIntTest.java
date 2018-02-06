package org.mimos.sentiment.application.web.rest;

import org.mimos.sentiment.application.KayhianMongoAccessApp;

import org.mimos.sentiment.application.domain.ClipExtract;
import org.mimos.sentiment.application.repository.ClipExtractRepository;
import org.mimos.sentiment.application.service.ClipExtractService;
import org.mimos.sentiment.application.repository.search.ClipExtractSearchRepository;
import org.mimos.sentiment.application.service.dto.ClipExtractDTO;
import org.mimos.sentiment.application.service.mapper.ClipExtractMapper;
import org.mimos.sentiment.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mimos.sentiment.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClipExtractResource REST controller.
 *
 * @see ClipExtractResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KayhianMongoAccessApp.class)
public class ClipExtractResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LANGUAGE = 1;
    private static final Integer UPDATED_LANGUAGE = 2;

    private static final Integer DEFAULT_PROCESSSTAGE = 1;
    private static final Integer UPDATED_PROCESSSTAGE = 2;

    @Autowired
    private ClipExtractRepository clipExtractRepository;

    @Autowired
    private ClipExtractMapper clipExtractMapper;

    @Autowired
    private ClipExtractService clipExtractService;

    @Autowired
    private ClipExtractSearchRepository clipExtractSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restClipExtractMockMvc;

    private ClipExtract clipExtract;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClipExtractResource clipExtractResource = new ClipExtractResource(clipExtractService);
        this.restClipExtractMockMvc = MockMvcBuilders.standaloneSetup(clipExtractResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClipExtract createEntity() {
        ClipExtract clipExtract = new ClipExtract()
            .title(DEFAULT_TITLE)
            .createddate(DEFAULT_CREATEDDATE)
            .url(DEFAULT_URL)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE)
            .processstage(DEFAULT_PROCESSSTAGE);
        return clipExtract;
    }

    @Before
    public void initTest() {
        clipExtractRepository.deleteAll();
        clipExtractSearchRepository.deleteAll();
        clipExtract = createEntity();
    }

    @Test
    public void createClipExtract() throws Exception {
        int databaseSizeBeforeCreate = clipExtractRepository.findAll().size();

        // Create the ClipExtract
        ClipExtractDTO clipExtractDTO = clipExtractMapper.toDto(clipExtract);
        restClipExtractMockMvc.perform(post("/api/clip-extracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clipExtractDTO)))
            .andExpect(status().isCreated());

        // Validate the ClipExtract in the database
        List<ClipExtract> clipExtractList = clipExtractRepository.findAll();
        assertThat(clipExtractList).hasSize(databaseSizeBeforeCreate + 1);
        ClipExtract testClipExtract = clipExtractList.get(clipExtractList.size() - 1);
        assertThat(testClipExtract.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClipExtract.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);
        assertThat(testClipExtract.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testClipExtract.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClipExtract.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClipExtract.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testClipExtract.getProcessstage()).isEqualTo(DEFAULT_PROCESSSTAGE);

        // Validate the ClipExtract in Elasticsearch
        ClipExtract clipExtractEs = clipExtractSearchRepository.findOne(testClipExtract.getId());
        assertThat(clipExtractEs).isEqualToIgnoringGivenFields(testClipExtract);
    }

    @Test
    public void createClipExtractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clipExtractRepository.findAll().size();

        // Create the ClipExtract with an existing ID
        clipExtract.setId("existing_id");
        ClipExtractDTO clipExtractDTO = clipExtractMapper.toDto(clipExtract);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClipExtractMockMvc.perform(post("/api/clip-extracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clipExtractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClipExtract in the database
        List<ClipExtract> clipExtractList = clipExtractRepository.findAll();
        assertThat(clipExtractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllClipExtracts() throws Exception {
        // Initialize the database
        clipExtractRepository.save(clipExtract);

        // Get all the clipExtractList
        restClipExtractMockMvc.perform(get("/api/clip-extracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clipExtract.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].processstage").value(hasItem(DEFAULT_PROCESSSTAGE)));
    }

    @Test
    public void getClipExtract() throws Exception {
        // Initialize the database
        clipExtractRepository.save(clipExtract);

        // Get the clipExtract
        restClipExtractMockMvc.perform(get("/api/clip-extracts/{id}", clipExtract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clipExtract.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.processstage").value(DEFAULT_PROCESSSTAGE));
    }

    @Test
    public void getNonExistingClipExtract() throws Exception {
        // Get the clipExtract
        restClipExtractMockMvc.perform(get("/api/clip-extracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClipExtract() throws Exception {
        // Initialize the database
        clipExtractRepository.save(clipExtract);
        clipExtractSearchRepository.save(clipExtract);
        int databaseSizeBeforeUpdate = clipExtractRepository.findAll().size();

        // Update the clipExtract
        ClipExtract updatedClipExtract = clipExtractRepository.findOne(clipExtract.getId());
        updatedClipExtract
            .title(UPDATED_TITLE)
            .createddate(UPDATED_CREATEDDATE)
            .url(UPDATED_URL)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE)
            .processstage(UPDATED_PROCESSSTAGE);
        ClipExtractDTO clipExtractDTO = clipExtractMapper.toDto(updatedClipExtract);

        restClipExtractMockMvc.perform(put("/api/clip-extracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clipExtractDTO)))
            .andExpect(status().isOk());

        // Validate the ClipExtract in the database
        List<ClipExtract> clipExtractList = clipExtractRepository.findAll();
        assertThat(clipExtractList).hasSize(databaseSizeBeforeUpdate);
        ClipExtract testClipExtract = clipExtractList.get(clipExtractList.size() - 1);
        assertThat(testClipExtract.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClipExtract.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);
        assertThat(testClipExtract.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testClipExtract.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClipExtract.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClipExtract.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testClipExtract.getProcessstage()).isEqualTo(UPDATED_PROCESSSTAGE);

        // Validate the ClipExtract in Elasticsearch
        ClipExtract clipExtractEs = clipExtractSearchRepository.findOne(testClipExtract.getId());
        assertThat(clipExtractEs).isEqualToIgnoringGivenFields(testClipExtract);
    }

    @Test
    public void updateNonExistingClipExtract() throws Exception {
        int databaseSizeBeforeUpdate = clipExtractRepository.findAll().size();

        // Create the ClipExtract
        ClipExtractDTO clipExtractDTO = clipExtractMapper.toDto(clipExtract);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClipExtractMockMvc.perform(put("/api/clip-extracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clipExtractDTO)))
            .andExpect(status().isCreated());

        // Validate the ClipExtract in the database
        List<ClipExtract> clipExtractList = clipExtractRepository.findAll();
        assertThat(clipExtractList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteClipExtract() throws Exception {
        // Initialize the database
        clipExtractRepository.save(clipExtract);
        clipExtractSearchRepository.save(clipExtract);
        int databaseSizeBeforeDelete = clipExtractRepository.findAll().size();

        // Get the clipExtract
        restClipExtractMockMvc.perform(delete("/api/clip-extracts/{id}", clipExtract.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean clipExtractExistsInEs = clipExtractSearchRepository.exists(clipExtract.getId());
        assertThat(clipExtractExistsInEs).isFalse();

        // Validate the database is empty
        List<ClipExtract> clipExtractList = clipExtractRepository.findAll();
        assertThat(clipExtractList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchClipExtract() throws Exception {
        // Initialize the database
        clipExtractRepository.save(clipExtract);
        clipExtractSearchRepository.save(clipExtract);

        // Search the clipExtract
        restClipExtractMockMvc.perform(get("/api/_search/clip-extracts?query=id:" + clipExtract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clipExtract.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].processstage").value(hasItem(DEFAULT_PROCESSSTAGE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClipExtract.class);
        ClipExtract clipExtract1 = new ClipExtract();
        clipExtract1.setId("id1");
        ClipExtract clipExtract2 = new ClipExtract();
        clipExtract2.setId(clipExtract1.getId());
        assertThat(clipExtract1).isEqualTo(clipExtract2);
        clipExtract2.setId("id2");
        assertThat(clipExtract1).isNotEqualTo(clipExtract2);
        clipExtract1.setId(null);
        assertThat(clipExtract1).isNotEqualTo(clipExtract2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClipExtractDTO.class);
        ClipExtractDTO clipExtractDTO1 = new ClipExtractDTO();
        clipExtractDTO1.setId("id1");
        ClipExtractDTO clipExtractDTO2 = new ClipExtractDTO();
        assertThat(clipExtractDTO1).isNotEqualTo(clipExtractDTO2);
        clipExtractDTO2.setId(clipExtractDTO1.getId());
        assertThat(clipExtractDTO1).isEqualTo(clipExtractDTO2);
        clipExtractDTO2.setId("id2");
        assertThat(clipExtractDTO1).isNotEqualTo(clipExtractDTO2);
        clipExtractDTO1.setId(null);
        assertThat(clipExtractDTO1).isNotEqualTo(clipExtractDTO2);
    }
}
