package uk.dcgmackenzie.snookerTracker.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SessionResponse {
    @NotNull
    List<Integer> results;
    @NotNull
    Boolean isHighScore;
}
