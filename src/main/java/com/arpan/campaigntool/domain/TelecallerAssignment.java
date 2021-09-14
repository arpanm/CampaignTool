package com.arpan.campaigntool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TelecallerAssignment.
 */
@Entity
@Table(name = "telecaller_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TelecallerAssignment implements Serializable {

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
    @JsonIgnoreProperties(value = { "disposition", "attributes", "client" }, allowSetters = true)
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TelecallerAssignment id(Long id) {
        this.id = id;
        return this;
    }

    public String getAssignmentDate() {
        return this.assignmentDate;
    }

    public TelecallerAssignment assignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
        return this;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public TelecallerAssignment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public TelecallerAssignment createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public TelecallerAssignment updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public TelecallerAssignment updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Telecaller getTelecaller() {
        return this.telecaller;
    }

    public TelecallerAssignment telecaller(Telecaller telecaller) {
        this.setTelecaller(telecaller);
        return this;
    }

    public void setTelecaller(Telecaller telecaller) {
        this.telecaller = telecaller;
    }

    public Campaign getCampaign() {
        return this.campaign;
    }

    public TelecallerAssignment campaign(Campaign campaign) {
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
        if (!(o instanceof TelecallerAssignment)) {
            return false;
        }
        return id != null && id.equals(((TelecallerAssignment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelecallerAssignment{" +
            "id=" + getId() +
            ", assignmentDate='" + getAssignmentDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
