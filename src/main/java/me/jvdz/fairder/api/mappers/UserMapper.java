package me.jvdz.fairder.api.mappers;

import me.jvdz.fairder.api.dtos.UserResponse;
import me.jvdz.fairder.api.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
 public UserResponse toResponse(User user) {
  return new UserResponse(
      user.getId(),
      user.getKeycloakSubject(),
      user.getEmail(),
      user.getDisplayName(),
      user.getPrimaryRole(),
      user.getStatus(),
      user.getEmploymentType(),
      user.getIntermediaryName(),
      user.getIntermediaryKvkNumber(),
      user.getVerificationStatus(),
      user.getCreatedAt(),
      user.getUpdatedAt()
  );
 }






}
