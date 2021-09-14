package com.arpan.campaigntool.service.dto;

import com.arpan.campaigntool.domain.enumeration.CallStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.arpan.campaigntool.domain.Call} entity.
 */
public class CallDTO implements Serializable {

    private Long id;

    private String phone;

    private String callDate;

    private String followupDate;

    private String notes;

    private String recordingUrl;

    private CallStatus status;

    private String createdBy;

    private LocalDate createdAt;

    private String updatedBy;

    private LocalDate updatedAt;

    private LeadAssignmentDTO leadAssignment;

    private DispositionSubmissionDTO dispositionSubmission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRecordingUrl() {
        return recordingUrl;
    }

    public void setRecordingUrl(String recordingUrl) {
        this.recordingUrl = recordingUrl;
    }

    public CallStatus getStatus() {
        return status;
    }

    public void setStatus(CallStatus status) {
        this.status = status;
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

    public LeadAssignmentDTO getLeadAssignment() {
        return leadAssignment;
    }

    public void setLeadAssignment(LeadAssignmentDTO leadAssignment) {
        this.leadAssignment = leadAssignment;
    }

    public DispositionSubmissionDTO getDispositionSubmission() {
        return dispositionSubmission;
    }

    public void setDispositionSubmission(DispositionSubmissionDTO dispositionSubmission) {
        this.dispositionSubmission = dispositionSubmission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CallDTO)) {
            return false;
        }

        CallDTO callDTO = (CallDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, callDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CallDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", callDate='" + getCallDate() + "'" +
            ", followupDate='" + getFollowupDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", recordingUrl='" + getRecordingUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", leadAssignment=" + getLeadAssignment() +
            ", dispositionSubmission=" + getDispositionSubmission() +
            "}";
    }
}
