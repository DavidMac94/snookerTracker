package uk.dcgmackenzie.snookerTracker.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uk.dcgmackenzie.snookerTracker.entities.Result;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResultResponse {
    @NotNull
    List<Result> scores;
    @NotNull
    Boolean isHighScore;
}
