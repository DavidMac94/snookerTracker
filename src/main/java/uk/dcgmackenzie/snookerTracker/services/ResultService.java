package uk.dcgmackenzie.snookerTracker.services;

import uk.dcgmackenzie.snookerTracker.DTO.ResultDTO;
import uk.dcgmackenzie.snookerTracker.entities.Result;
import uk.dcgmackenzie.snookerTracker.response.ResultResponse;

import java.util.List;

public interface ResultService {
    ResultResponse saveResults(ResultDTO resultDTO, String username);
    List<Result> getResults(String username);
    Result getResult(Long id, String username);
}
