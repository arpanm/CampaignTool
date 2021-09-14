package com.arpan.campaigntool.service.dto;

import com.arpan.campaigntool.domain.enumeration.InOutType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.arpan.campaigntool.domain.TelecallerInOut} entity.
 */
public class TelecallerInOutDTO implements Serializable {

    private Long id;

    private InOutType eventType;

    private LocalDate eventTime;

    private String eventDate;

    private String createdBy;

    private LocalDate createdAt;

    private String updatedBy;

    private LocalDate updatedAt;

    private TelecallerDTO telecaller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InOutType getEventType() {
        return eventType;
    }

    public void setEventType(InOutType eventType) {
        this.eventType = eventType;
    }

    public LocalDate getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDate eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelecallerInOutDTO)) {
            return false;
        }

        TelecallerInOutDTO telecallerInOutDTO = (TelecallerInOutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, telecallerInOutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelecallerInOutDTO{" +
            "id=" + getId() +
            ", eventType='" + getEventType() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", telecaller=" + getTelecaller() +
            "}";
    }
}
