package me.jvdz.fairder.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import me.jvdz.fairder.api.entities.ShiftStatus;
import me.jvdz.fairder.api.entities.UrgencyClass;

/**
 * Read-only API representation of a Fairder user profile.
 *
 * <p>This DTO exposes the Keycloak link, profile details, role, employment
 * context, verification state, and audit timestamps. It deliberately contains
 * no credentials because authentication is handled by Keycloak.</p>
 */
public record ShiftResponse(
    Long id,
    Long employerId,
    Long reservePoolId,
    String title,
    String location,
    BigDecimal latitude,
    BigDecimal longitude,
    LocalDateTime startTime,
    LocalDateTime endTime,
    BigDecimal baseRate,
    ShiftStatus status,
    UrgencyClass urgencyClass,
    boolean premium,
    Long autoAssignedBookingId,
    LocalDateTime createdAt
) {}
