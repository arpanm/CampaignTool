package com.arpan.campaigntool.domain;

import com.arpan.campaigntool.domain.enumeration.CompanyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_website")
    private String companyWebsite;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private CompanyType companyType;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "description")
    private String description;

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

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "disposition", "attributes", "client" }, allowSetters = true)
    private Set<Campaign> campaigns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client id(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Client companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWebsite() {
        return this.companyWebsite;
    }

    public Client companyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
        return this;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public CompanyType getCompanyType() {
        return this.companyType;
    }

    public Client companyType(CompanyType companyType) {
        this.companyType = companyType;
        return this;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public String getPrimaryPhone() {
        return this.primaryPhone;
    }

    public Client primaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
        return this;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getDescription() {
        return this.description;
    }

    public Client description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Client isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Client createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Client createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Client updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Client updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Campaign> getCampaigns() {
        return this.campaigns;
    }

    public Client campaigns(Set<Campaign> campaigns) {
        this.setCampaigns(campaigns);
        return this;
    }

    public Client addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
        campaign.setClient(this);
        return this;
    }

    public Client removeCampaign(Campaign campaign) {
        this.campaigns.remove(campaign);
        campaign.setClient(null);
        return this;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        if (this.campaigns != null) {
            this.campaigns.forEach(i -> i.setClient(null));
        }
        if (campaigns != null) {
            campaigns.forEach(i -> i.setClient(this));
        }
        this.campaigns = campaigns;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyWebsite='" + getCompanyWebsite() + "'" +
            ", companyType='" + getCompanyType() + "'" +
            ", primaryPhone='" + getPrimaryPhone() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
