package com.arpan.campaigntool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DispositionSubmission.
 */
@Entity
@Table(name = "disposition_submission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DispositionSubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @JsonIgnoreProperties(value = { "leadAssignment", "dispositionSubmission" }, allowSetters = true)
    @OneToOne(mappedBy = "dispositionSubmission")
    private Call call;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fields", "dispositionSubmissions", "campaign" }, allowSetters = true)
    private Disposition disposition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DispositionSubmission id(Long id) {
        this.id = id;
        return this;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DispositionSubmission createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public DispositionSubmission createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DispositionSubmission updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public DispositionSubmission updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Call getCall() {
        return this.call;
    }

    public DispositionSubmission call(Call call) {
        this.setCall(call);
        return this;
    }

    public void setCall(Call call) {
        if (this.call != null) {
            this.call.setDispositionSubmission(null);
        }
        if (call != null) {
            call.setDispositionSubmission(this);
        }
        this.call = call;
    }

    public Disposition getDisposition() {
        return this.disposition;
    }

    public DispositionSubmission disposition(Disposition disposition) {
        this.setDisposition(disposition);
        return this;
    }

    public void setDisposition(Disposition disposition) {
        this.disposition = disposition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispositionSubmission)) {
            return false;
        }
        return id != null && id.equals(((DispositionSubmission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispositionSubmission{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
