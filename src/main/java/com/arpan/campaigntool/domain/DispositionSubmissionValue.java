package com.arpan.campaigntool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DispositionSubmissionValue.
 */
@Entity
@Table(name = "disposition_submission_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DispositionSubmissionValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "call", "disposition" }, allowSetters = true)
    private DispositionSubmission dispositionSubmission;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fieldPossibleValues", "disposition" }, allowSetters = true)
    private Field field;

    @ManyToOne
    @JsonIgnoreProperties(value = { "field" }, allowSetters = true)
    private FieldPossibleValue possibleValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DispositionSubmissionValue id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public DispositionSubmissionValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DispositionSubmissionValue createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public DispositionSubmissionValue createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DispositionSubmissionValue updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public DispositionSubmissionValue updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DispositionSubmission getDispositionSubmission() {
        return this.dispositionSubmission;
    }

    public DispositionSubmissionValue dispositionSubmission(DispositionSubmission dispositionSubmission) {
        this.setDispositionSubmission(dispositionSubmission);
        return this;
    }

    public void setDispositionSubmission(DispositionSubmission dispositionSubmission) {
        this.dispositionSubmission = dispositionSubmission;
    }

    public Field getField() {
        return this.field;
    }

    public DispositionSubmissionValue field(Field field) {
        this.setField(field);
        return this;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public FieldPossibleValue getPossibleValue() {
        return this.possibleValue;
    }

    public DispositionSubmissionValue possibleValue(FieldPossibleValue fieldPossibleValue) {
        this.setPossibleValue(fieldPossibleValue);
        return this;
    }

    public void setPossibleValue(FieldPossibleValue fieldPossibleValue) {
        this.possibleValue = fieldPossibleValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositionSubmissionValue)) {
            return false;
        }
        return id != null && id.equals(((DispositionSubmissionValue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositionSubmissionValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
