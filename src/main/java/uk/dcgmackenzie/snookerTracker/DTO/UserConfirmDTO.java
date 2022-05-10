package uk.dcgmackenzie.snookerTracker.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserConfirmDTO {
    @NotNull
    private String username;
    @NotNull
    private String verificationCode;
}
