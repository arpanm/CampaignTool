package com.reliance.jpl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reliance.jpl.domain.enumeration.InOutType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TelecallerInOut.
 */
@Entity
@Table(name = "telecaller_in_out")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelecallerInOut implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private InOutType eventType;

    @Column(name = "event_time")
    private LocalDate eventTime;

    @Column(name = "event_date")
    private String eventDate;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TelecallerInOut id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InOutType getEventType() {
        return this.eventType;
    }

    public TelecallerInOut eventType(InOutType eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(InOutType eventType) {
        this.eventType = eventType;
    }

    public LocalDate getEventTime() {
        return this.eventTime;
    }

    public TelecallerInOut eventTime(LocalDate eventTime) {
        this.setEventTime(eventTime);
        return this;
    }

    public void setEventTime(LocalDate eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public TelecallerInOut eventDate(String eventDate) {
        this.setEventDate(eventDate);
        return this;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public TelecallerInOut createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public TelecallerInOut createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public TelecallerInOut updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public TelecallerInOut updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Telecaller getTelecaller() {
        return this.telecaller;
    }

    public void setTelecaller(Telecaller telecaller) {
        this.telecaller = telecaller;
    }

    public TelecallerInOut telecaller(Telecaller telecaller) {
        this.setTelecaller(telecaller);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelecallerInOut)) {
            return false;
        }
        return id != null && id.equals(((TelecallerInOut) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelecallerInOut{" +
            "id=" + getId() +
            ", eventType='" + getEventType() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
