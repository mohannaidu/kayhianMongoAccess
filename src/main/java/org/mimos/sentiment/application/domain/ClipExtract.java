package org.mimos.sentiment.application.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ClipExtract.
 */
@Document(collection = "clip_extract")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clipextract")
public class ClipExtract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("createddate")
    private Instant createddate;

    @Field("url")
    private String url;

    @Field("type")
    private Integer type;

    @Field("description")
    private String description;

    @Field("language")
    private Integer language;

    @Field("processstage")
    private Integer processstage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ClipExtract title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreateddate() {
        return createddate;
    }

    public ClipExtract createddate(Instant createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(Instant createddate) {
        this.createddate = createddate;
    }

    public String getUrl() {
        return url;
    }

    public ClipExtract url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public ClipExtract type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public ClipExtract description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLanguage() {
        return language;
    }

    public ClipExtract language(Integer language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getProcessstage() {
        return processstage;
    }

    public ClipExtract processstage(Integer processstage) {
        this.processstage = processstage;
        return this;
    }

    public void setProcessstage(Integer processstage) {
        this.processstage = processstage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClipExtract clipExtract = (ClipExtract) o;
        if (clipExtract.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clipExtract.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClipExtract{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", createddate='" + getCreateddate() + "'" +
            ", url='" + getUrl() + "'" +
            ", type=" + getType() +
            ", description='" + getDescription() + "'" +
            ", language=" + getLanguage() +
            ", processstage=" + getProcessstage() +
            "}";
    }
}
