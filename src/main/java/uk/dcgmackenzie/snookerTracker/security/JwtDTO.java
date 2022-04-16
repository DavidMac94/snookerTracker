package uk.dcgmackenzie.snookerTracker.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtDTO {
    private final String accessToken;
    private final String refreshToken;
}
