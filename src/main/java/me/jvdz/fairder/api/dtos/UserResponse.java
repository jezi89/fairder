package me.jvdz.fairder.api.dtos;

import java.time.LocalDateTime;
import me.jvdz.fairder.api.entities.UserPrimaryRole;
import me.jvdz.fairder.api.entities.UserStatus;

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
