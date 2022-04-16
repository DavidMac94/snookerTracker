package uk.dcgmackenzie.snookerTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class ResultDTO {
    @NotNull
    private final List<Integer> scores;
    @NotNull
    private final Long sessionId;
}
