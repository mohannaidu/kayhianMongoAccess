package org.mimos.sentiment.application.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClipExtract entity.
 */
public class ClipExtractDTO implements Serializable {

    private String id;

    private String title;

    private Instant createddate;

    private String url;

    private Integer type;

    private String description;

    private Integer language;

    private Integer processstage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Instant createddate) {
        this.createddate = createddate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getProcessstage() {
        return processstage;
    }

    public void setProcessstage(Integer processstage) {
        this.processstage = processstage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClipExtractDTO clipExtractDTO = (ClipExtractDTO) o;
        if(clipExtractDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clipExtractDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClipExtractDTO{" +
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
