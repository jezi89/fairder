package me.jvdz.fairder.api.dtos;

import java.time.LocalDateTime;
import me.jvdz.fairder.api.entities.UserPrimaryRole;
import me.jvdz.fairder.api.entities.UserStatus;
import me.jvdz.fairder.api.entities.VerificationStatus;

/**
 * Read-only API representation of a Fairder user profile.
 *
 * <p>This DTO exposes the Keycloak link, profile details, role, employment
 * context, verification state, and audit timestamps. It deliberately contains
 * no credentials because authentication is handled by Keycloak.</p>
 */
public record UserResponse(
    Long id,
    String keycloakSubject,
    String email,
    String displayName,
    UserPrimaryRole primaryRole,
    UserStatus status,
    EmploymentType employmentType,
    String IntermediaryName,
    String intermediaryKvkNumber,
    VerificationStatus verificationStatus,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
