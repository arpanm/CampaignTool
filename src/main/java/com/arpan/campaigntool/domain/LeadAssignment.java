package com.arpan.campaigntool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LeadAssignment.
 */
@Entity
@Table(name = "lead_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeadAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_date")
    private String assignmentDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "telecallerInOuts" }, allowSetters = true)
    private Telecaller telecaller;

    @ManyToOne
    @JsonIgnoreProperties(value = { "attributes", "locations" }, allowSetters = true)
    private Lead lead;

    @JsonIgnoreProperties(value = { "leadAssignment", "dispositionSubmission" }, allowSetters = true)
    @OneToOne(mappedBy = "leadAssignment")
    private Call call;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeadAssignment id(Long id) {
        this.id = id;
        return this;
    }

    public String getAssignmentDate() {
        return this.assignmentDate;
    }

    public LeadAssignment assignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
        return this;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LeadAssignment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public LeadAssignment createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public LeadAssignment updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public LeadAssignment updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Telecaller getTelecaller() {
        return this.telecaller;
    }

    public LeadAssignment telecaller(Telecaller telecaller) {
        this.setTelecaller(telecaller);
        return this;
    }

    public void setTelecaller(Telecaller telecaller) {
        this.telecaller = telecaller;
    }

    public Lead getLead() {
        return this.lead;
    }

    public LeadAssignment lead(Lead lead) {
        this.setLead(lead);
        return this;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public Call getCall() {
        return this.call;
    }

    public LeadAssignment call(Call call) {
        this.setCall(call);
        return this;
    }

    public void setCall(Call call) {
        if (this.call != null) {
            this.call.setLeadAssignment(null);
        }
        if (call != null) {
            call.setLeadAssignment(this);
        }
        this.call = call;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeadAssignment)) {
            return false;
        }
        return id != null && id.equals(((LeadAssignment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeadAssignment{" +
            "id=" + getId() +
            ", assignmentDate='" + getAssignmentDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
