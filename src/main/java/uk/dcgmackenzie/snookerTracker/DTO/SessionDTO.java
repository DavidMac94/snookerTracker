package uk.dcgmackenzie.snookerTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class SessionDTO {
    @NotNull
    private final Long drillId;
    @NotNull
    private final List<Integer> results;
}
