package com.arpan.campaigntool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attribute.
 */
@Entity
@Table(name = "attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attribute implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "key" }, allowSetters = true)
    private AttributePossibleValue value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "attributePossibleValues" }, allowSetters = true)
    private AttributeKey key;

    @ManyToOne
    @JsonIgnoreProperties(value = { "attributes", "locations" }, allowSetters = true)
    private Lead lead;

    @ManyToOne
    @JsonIgnoreProperties(value = { "disposition", "attributes", "client" }, allowSetters = true)
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attribute id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Attribute isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Attribute createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Attribute createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Attribute updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Attribute updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AttributePossibleValue getValue() {
        return this.value;
    }

    public Attribute value(AttributePossibleValue attributePossibleValue) {
        this.setValue(attributePossibleValue);
        return this;
    }

    public void setValue(AttributePossibleValue attributePossibleValue) {
        this.value = attributePossibleValue;
    }

    public AttributeKey getKey() {
        return this.key;
    }

    public Attribute key(AttributeKey attributeKey) {
        this.setKey(attributeKey);
        return this;
    }

    public void setKey(AttributeKey attributeKey) {
        this.key = attributeKey;
    }

    public Lead getLead() {
        return this.lead;
    }

    public Attribute lead(Lead lead) {
        this.setLead(lead);
        return this;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public Campaign getCampaign() {
        return this.campaign;
    }

    public Attribute campaign(Campaign campaign) {
        this.setCampaign(campaign);
        return this;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        return id != null && id.equals(((Attribute) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attribute{" +
            "id=" + getId() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
