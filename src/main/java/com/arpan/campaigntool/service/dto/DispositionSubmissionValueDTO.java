package com.arpan.campaigntool.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.arpan.campaigntool.domain.DispositionSubmissionValue} entity.
 */
public class DispositionSubmissionValueDTO implements Serializable {

    private Long id;

    private String value;

    private String createdBy;

    private LocalDate createdAt;

    private String updatedBy;

    private LocalDate updatedAt;

    private DispositionSubmissionDTO dispositionSubmission;

    private FieldDTO field;

    private FieldPossibleValueDTO possibleValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DispositionSubmissionDTO getDispositionSubmission() {
        return dispositionSubmission;
    }

    public void setDispositionSubmission(DispositionSubmissionDTO dispositionSubmission) {
        this.dispositionSubmission = dispositionSubmission;
    }

    public FieldDTO getField() {
        return field;
    }

    public void setField(FieldDTO field) {
        this.field = field;
    }

    public FieldPossibleValueDTO getPossibleValue() {
        return possibleValue;
    }

    public void setPossibleValue(FieldPossibleValueDTO possibleValue) {
        this.possibleValue = possibleValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositionSubmissionValueDTO)) {
            return false;
        }

        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = (DispositionSubmissionValueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dispositionSubmissionValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositionSubmissionValueDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", dispositionSubmission=" + getDispositionSubmission() +
            ", field=" + getField() +
            ", possibleValue=" + getPossibleValue() +
            "}";
    }
}
