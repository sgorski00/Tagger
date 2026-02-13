package pl.sgorski.Tagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ProfileResponse(
  @Schema(description = "User's first and last name") String name,
  @Schema(description = "User's email address") String email,
  @Schema(description = "User's account creation timestamp") LocalDateTime createdAt
) { }
