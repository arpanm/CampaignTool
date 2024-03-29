package com.reliance.jpl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reliance.jpl.domain.enumeration.CallStatus;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Call.
 */
@Entity
@Table(name = "jhi_call")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Call implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "call_date")
    private String callDate;

    @Column(name = "followup_date")
    private String followupDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "recording_url")
    private String recordingUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CallStatus status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @JsonIgnoreProperties(value = { "telecaller", "lead", "call" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private LeadAssignment leadAssignment;

    @JsonIgnoreProperties(value = { "call", "disposition" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private DispositionSubmission dispositionSubmission;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Call id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return this.phone;
    }

    public Call phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCallDate() {
        return this.callDate;
    }

    public Call callDate(String callDate) {
        this.setCallDate(callDate);
        return this;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getFollowupDate() {
        return this.followupDate;
    }

    public Call followupDate(String followupDate) {
        this.setFollowupDate(followupDate);
        return this;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Call notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRecordingUrl() {
        return this.recordingUrl;
    }

    public Call recordingUrl(String recordingUrl) {
        this.setRecordingUrl(recordingUrl);
        return this;
    }

    public void setRecordingUrl(String recordingUrl) {
        this.recordingUrl = recordingUrl;
    }

    public CallStatus getStatus() {
        return this.status;
    }

    public Call status(CallStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CallStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Call createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Call createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Call updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Call updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LeadAssignment getLeadAssignment() {
        return this.leadAssignment;
    }

    public void setLeadAssignment(LeadAssignment leadAssignment) {
        this.leadAssignment = leadAssignment;
    }

    public Call leadAssignment(LeadAssignment leadAssignment) {
        this.setLeadAssignment(leadAssignment);
        return this;
    }

    public DispositionSubmission getDispositionSubmission() {
        return this.dispositionSubmission;
    }

    public void setDispositionSubmission(DispositionSubmission dispositionSubmission) {
        this.dispositionSubmission = dispositionSubmission;
    }

    public Call dispositionSubmission(DispositionSubmission dispositionSubmission) {
        this.setDispositionSubmission(dispositionSubmission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Call)) {
            return false;
        }
        return id != null && id.equals(((Call) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Call{" +
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
            "}";
    }
}
