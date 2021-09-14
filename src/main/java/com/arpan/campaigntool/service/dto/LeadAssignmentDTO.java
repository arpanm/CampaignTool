package com.arpan.campaigntool.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.arpan.campaigntool.domain.LeadAssignment} entity.
 */
public class LeadAssignmentDTO implements Serializable {

    private Long id;

    private String assignmentDate;

    private String createdBy;

    private LocalDate createdAt;

    private String updatedBy;

    private LocalDate updatedAt;

    private TelecallerDTO telecaller;

    private LeadDTO lead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
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

    public TelecallerDTO getTelecaller() {
        return telecaller;
    }

    public void setTelecaller(TelecallerDTO telecaller) {
        this.telecaller = telecaller;
    }

    public LeadDTO getLead() {
        return lead;
    }

    public void setLead(LeadDTO lead) {
        this.lead = lead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeadAssignmentDTO)) {
            return false;
        }

        LeadAssignmentDTO leadAssignmentDTO = (LeadAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leadAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeadAssignmentDTO{" +
            "id=" + getId() +
            ", assignmentDate='" + getAssignmentDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", telecaller=" + getTelecaller() +
            ", lead=" + getLead() +
            "}";
    }
}
