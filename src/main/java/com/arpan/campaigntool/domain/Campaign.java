package com.arpan.campaigntool.domain;

import com.arpan.campaigntool.domain.enumeration.CampaignApprovalStatus;
import com.arpan.campaigntool.domain.enumeration.CampaignType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CampaignType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CampaignApprovalStatus status;

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

    @JsonIgnoreProperties(value = { "fields", "dispositionSubmissions", "campaign" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Disposition disposition;

    @OneToMany(mappedBy = "campaign")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "value", "key", "lead", "campaign" }, allowSetters = true)
    private Set<Attribute> attributes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "campaigns" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campaign id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Campaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Campaign description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Campaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Campaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public CampaignType getType() {
        return this.type;
    }

    public Campaign type(CampaignType type) {
        this.type = type;
        return this;
    }

    public void setType(CampaignType type) {
        this.type = type;
    }

    public CampaignApprovalStatus getStatus() {
        return this.status;
    }

    public Campaign status(CampaignApprovalStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CampaignApprovalStatus status) {
        this.status = status;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Campaign isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Campaign createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Campaign createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Campaign updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Campaign updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Disposition getDisposition() {
        return this.disposition;
    }

    public Campaign disposition(Disposition disposition) {
        this.setDisposition(disposition);
        return this;
    }

    public void setDisposition(Disposition disposition) {
        this.disposition = disposition;
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public Campaign attributes(Set<Attribute> attributes) {
        this.setAttributes(attributes);
        return this;
    }

    public Campaign addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
        attribute.setCampaign(this);
        return this;
    }

    public Campaign removeAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.setCampaign(null);
        return this;
    }

    public void setAttributes(Set<Attribute> attributes) {
        if (this.attributes != null) {
            this.attributes.forEach(i -> i.setCampaign(null));
        }
        if (attributes != null) {
            attributes.forEach(i -> i.setCampaign(this));
        }
        this.attributes = attributes;
    }

    public Client getClient() {
        return this.client;
    }

    public Campaign client(Client client) {
        this.setClient(client);
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        return id != null && id.equals(((Campaign) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
