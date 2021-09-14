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
 * A AttributeKey.
 */
@Entity
@Table(name = "attribute_key")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttributeKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_key")
    private String key;

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

    @OneToMany(mappedBy = "key")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "key" }, allowSetters = true)
    private Set<AttributePossibleValue> attributePossibleValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttributeKey id(Long id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public AttributeKey key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public AttributeKey isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AttributeKey createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public AttributeKey createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public AttributeKey updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public AttributeKey updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AttributePossibleValue> getAttributePossibleValues() {
        return this.attributePossibleValues;
    }

    public AttributeKey attributePossibleValues(Set<AttributePossibleValue> attributePossibleValues) {
        this.setAttributePossibleValues(attributePossibleValues);
        return this;
    }

    public AttributeKey addAttributePossibleValue(AttributePossibleValue attributePossibleValue) {
        this.attributePossibleValues.add(attributePossibleValue);
        attributePossibleValue.setKey(this);
        return this;
    }

    public AttributeKey removeAttributePossibleValue(AttributePossibleValue attributePossibleValue) {
        this.attributePossibleValues.remove(attributePossibleValue);
        attributePossibleValue.setKey(null);
        return this;
    }

    public void setAttributePossibleValues(Set<AttributePossibleValue> attributePossibleValues) {
        if (this.attributePossibleValues != null) {
            this.attributePossibleValues.forEach(i -> i.setKey(null));
        }
        if (attributePossibleValues != null) {
            attributePossibleValues.forEach(i -> i.setKey(this));
        }
        this.attributePossibleValues = attributePossibleValues;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeKey)) {
            return false;
        }
        return id != null && id.equals(((AttributeKey) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributeKey{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
