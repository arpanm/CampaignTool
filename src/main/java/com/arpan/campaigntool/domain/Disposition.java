package com.arpan.campaigntool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Disposition.
 */
@Entity
@Table(name = "disposition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Disposition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "disposition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fieldPossibleValues", "disposition" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    @OneToMany(mappedBy = "disposition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "call", "disposition" }, allowSetters = true)
    private Set<DispositionSubmission> dispositionSubmissions = new HashSet<>();

    @JsonIgnoreProperties(value = { "disposition", "attributes", "client" }, allowSetters = true)
    @OneToOne(mappedBy = "disposition")
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disposition id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Disposition isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Disposition createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Disposition createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Disposition updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Disposition updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public Disposition fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public Disposition addField(Field field) {
        this.fields.add(field);
        field.setDisposition(this);
        return this;
    }

    public Disposition removeField(Field field) {
        this.fields.remove(field);
        field.setDisposition(null);
        return this;
    }

    public void setFields(Set<Field> fields) {
        if (this.fields != null) {
            this.fields.forEach(i -> i.setDisposition(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setDisposition(this));
        }
        this.fields = fields;
    }

    public Set<DispositionSubmission> getDispositionSubmissions() {
        return this.dispositionSubmissions;
    }

    public Disposition dispositionSubmissions(Set<DispositionSubmission> dispositionSubmissions) {
        this.setDispositionSubmissions(dispositionSubmissions);
        return this;
    }

    public Disposition addDispositionSubmission(DispositionSubmission dispositionSubmission) {
        this.dispositionSubmissions.add(dispositionSubmission);
        dispositionSubmission.setDisposition(this);
        return this;
    }

    public Disposition removeDispositionSubmission(DispositionSubmission dispositionSubmission) {
        this.dispositionSubmissions.remove(dispositionSubmission);
        dispositionSubmission.setDisposition(null);
        return this;
    }

    public void setDispositionSubmissions(Set<DispositionSubmission> dispositionSubmissions) {
        if (this.dispositionSubmissions != null) {
            this.dispositionSubmissions.forEach(i -> i.setDisposition(null));
        }
        if (dispositionSubmissions != null) {
            dispositionSubmissions.forEach(i -> i.setDisposition(this));
        }
        this.dispositionSubmissions = dispositionSubmissions;
    }

    public Campaign getCampaign() {
        return this.campaign;
    }

    public Disposition campaign(Campaign campaign) {
        this.setCampaign(campaign);
        return this;
    }

    public void setCampaign(Campaign campaign) {
        if (this.campaign != null) {
            this.campaign.setDisposition(null);
        }
        if (campaign != null) {
            campaign.setDisposition(this);
        }
        this.campaign = campaign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disposition)) {
            return false;
        }
        return id != null && id.equals(((Disposition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disposition{" +
            "id=" + getId() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
