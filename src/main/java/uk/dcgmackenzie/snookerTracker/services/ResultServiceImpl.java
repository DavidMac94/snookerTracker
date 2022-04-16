package uk.dcgmackenzie.snookerTracker.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.dcgmackenzie.snookerTracker.DTO.ResultDTO;
import uk.dcgmackenzie.snookerTracker.entities.Result;
import uk.dcgmackenzie.snookerTracker.entities.Session;
import uk.dcgmackenzie.snookerTracker.repositories.ResultRepository;
import uk.dcgmackenzie.snookerTracker.response.ResultResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service @AllArgsConstructor
public class ResultServiceImpl implements ResultService{
    private final ResultRepository resultRepository;
    private final SessionService sessionService;

    @Override
    public ResultResponse saveResults(ResultDTO resultDTO, String username) {
        Session session = sessionService.getSession(resultDTO.getSessionId(), username);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session id " + resultDTO.getSessionId() + " does not exist");
        }
        Integer highScore = resultRepository.getHighScore(session.getDrill().getId(), username);
        Boolean isHighScore = highScore == null || Collections.max(resultDTO.getScores()) > highScore;
        System.out.println("High score:" + highScore);
        List<Result> results = new ArrayList<>();
        for (Integer score: resultDTO.getScores()) {
            Result result = new Result();
            result.setScore(score);
            result.setSession(session);
            results.add(resultRepository.save(result));
        }
        return new ResultResponse(results, isHighScore);
    }

    @Override
    public List<Result> getResults(String username) {
        return resultRepository.getResults(username);
    }

    @Override
    public Result getResult(Long id, String username) {
        Result result = resultRepository.getResult(id, username);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result id " + id + " not found");
        }
        return result;
    }
}
