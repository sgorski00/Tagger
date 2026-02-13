package pl.sgorski.Tagger.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sgorski.Tagger.dto.ProfileResponse;
import pl.sgorski.Tagger.service.auth.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<ProfileResponse> getProfile() {
    var response = userService.getLoggedUser();
    return ResponseEntity.ok(response);
  }
}
