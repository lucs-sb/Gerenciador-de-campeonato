package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Match;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.ChampionshipRepository;
import api.championship.manager.repositories.MatchRepository;
import api.championship.manager.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TeamRepository teamRepository;

    public Page<Match> getMatches(Long id, Pageable pageable) {
        try {
            Page<Match> matches = matchRepository.findByChampionshipId(id, pageable);
            if (matches.isEmpty())
                throw new MessageNotFoundException("Torneio sem partidas cadastradas");

            return matches;
        }catch (Exception e){
            throw e;
        }
    }

    public Match getMatchById(Long id) {
        try {
            Optional<Match> match = matchRepository.findById(id);
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            return match.get();
        }catch (Exception e){
            throw e;
        }
    }

    public void addMatch(Match newMatch) {
        try {
            Optional<Championship> championship = championshipRepository.findById(newMatch.getChampionship().getId());
            if (championship.isEmpty())
                throw new MessageNotFoundException("Torneio não encontrado");

            Optional<Team> home_team = teamRepository.findById(newMatch.getHome_team().getId());
            if (home_team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            Optional<Team> away_team = teamRepository.findById(newMatch.getAway_team().getId());
            if (away_team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            Match match = new Match();
            match.setChampionship(championship.get());
            match.setHome_team(home_team.get());
            match.setAway_team(away_team.get());
            match.setDate(newMatch.getDate());
            match.setTime(newMatch.getTime());
            match.setPlace(newMatch.getPlace());
            match.setScoreboard(newMatch.getScoreboard());

            matchRepository.save(match);
        }catch (Exception e){
            throw e;
        }
    }

    public void updateMatch(Long id, Match newMatch) {
        try {
            Optional<Match> match = matchRepository.findById(id);
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            Optional<Team> home_team = teamRepository.findById(newMatch.getHome_team().getId());
            if (home_team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            Optional<Team> away_team = teamRepository.findById(newMatch.getAway_team().getId());
            if (away_team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            match = Optional.of(new Match());
            match.get().setHome_team(home_team.get());
            match.get().setAway_team(away_team.get());
            match.get().setDate(newMatch.getDate());
            match.get().setTime(newMatch.getTime());
            match.get().setPlace(newMatch.getPlace());
            match.get().setScoreboard(newMatch.getScoreboard());

            matchRepository.save(match.get());
        }catch (Exception e){
            throw e;
        }
    }

    public void deleteMatch(Long id) {
        try {
            Optional<Match> match = matchRepository.findById(id);
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            matchRepository.delete(match.get());
        }catch (Exception e){
            throw e;
        }
    }
}
