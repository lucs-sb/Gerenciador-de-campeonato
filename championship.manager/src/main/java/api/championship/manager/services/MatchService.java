package api.championship.manager.services;

import api.championship.manager.enums.MatchStatus;
import api.championship.manager.enums.MatchType;
import api.championship.manager.execeptionHandler.exceptions.MessageBadRequestException;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Group;
import api.championship.manager.models.Match;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupInformationRepository groupInformationRepository;

    @Transactional(readOnly = true)
    public Page<Match> getMatches(Long id, Pageable pageable) {
        try {
            Page<Match> matches = matchRepository.findByChampionshipIdAndDeletionDateIsNull(id, pageable);
            if (matches.isEmpty())
                throw new MessageNotFoundException("Torneio sem partidas cadastradas");

            return matches;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
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

    @Transactional
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
            match.setType(newMatch.getType());
            match.setStatus(newMatch.getStatus());

            matchRepository.save(match);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateMatch(Long id, Match newMatch) {
        try {
            Optional<Match> match = matchRepository.findById(id);
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            match.get().setDate(newMatch.getDate());
            match.get().setTime(newMatch.getTime());
            match.get().setPlace(newMatch.getPlace());
            match.get().setScoreboard(newMatch.getScoreboard());
            match.get().setType(newMatch.getType());
            match.get().setStatus(newMatch.getStatus());

            matchRepository.save(match.get());
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteMatch(Long id) {
        try {
            Optional<Match> match = matchRepository.findById(id);
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            match.get().setDeletionDate(LocalDateTime.now());

            matchRepository.save(match.get());
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void createMatches(Championship championship, List<Group> groups) {
        try {
            groups.forEach(group -> {
                Collections.shuffle(group.getTeams());

                Match match1 = new Match();
                match1.setChampionship(championship);
                match1.setType(MatchType.GROUP_STAGE);
                match1.setStatus(MatchStatus.PROGRESS);
                match1.setHome_team(group.getTeams().get(0));
                match1.setAway_team(group.getTeams().get(2));
                match1.setJourney("Jornada 1");

                Match match2 = new Match();
                match2.setChampionship(championship);
                match2.setType(MatchType.GROUP_STAGE);
                match2.setStatus(MatchStatus.PROGRESS);
                match2.setHome_team(group.getTeams().get(1));
                match2.setAway_team(group.getTeams().get(3));
                match2.setJourney("Jornada 1");

                Match match3 = new Match();
                match3.setChampionship(championship);
                match3.setType(MatchType.GROUP_STAGE);
                match3.setStatus(MatchStatus.PROGRESS);
                match3.setHome_team(group.getTeams().get(3));
                match3.setAway_team(group.getTeams().get(0));
                match3.setJourney("Jornada 2");

                Match match4 = new Match();
                match4.setChampionship(championship);
                match4.setType(MatchType.GROUP_STAGE);
                match4.setStatus(MatchStatus.PROGRESS);
                match4.setHome_team(group.getTeams().get(2));
                match4.setAway_team(group.getTeams().get(1));
                match4.setJourney("Jornada 2");

                Match match5 = new Match();
                match5.setChampionship(championship);
                match5.setType(MatchType.GROUP_STAGE);
                match5.setStatus(MatchStatus.PROGRESS);
                match5.setHome_team(group.getTeams().get(2));
                match5.setAway_team(group.getTeams().get(3));
                match5.setJourney("Jornada 3");

                Match match6 = new Match();
                match6.setChampionship(championship);
                match6.setType(MatchType.GROUP_STAGE);
                match6.setStatus(MatchStatus.PROGRESS);
                match6.setHome_team(group.getTeams().get(1));
                match6.setAway_team(group.getTeams().get(0));
                match6.setJourney("Jornada 3");

                Match match7 = new Match();
                match7.setChampionship(championship);
                match7.setType(MatchType.GROUP_STAGE);
                match7.setStatus(MatchStatus.PROGRESS);
                match7.setHome_team(group.getTeams().get(3));
                match7.setAway_team(group.getTeams().get(2));
                match7.setJourney("Jornada 4");

                Match match8 = new Match();
                match8.setChampionship(championship);
                match8.setType(MatchType.GROUP_STAGE);
                match8.setStatus(MatchStatus.PROGRESS);
                match8.setHome_team(group.getTeams().get(0));
                match8.setAway_team(group.getTeams().get(1));
                match8.setJourney("Jornada 4");

                Match match9 = new Match();
                match9.setChampionship(championship);
                match9.setType(MatchType.GROUP_STAGE);
                match9.setStatus(MatchStatus.PROGRESS);
                match9.setHome_team(group.getTeams().get(0));
                match9.setAway_team(group.getTeams().get(3));
                match9.setJourney("Jornada 5");

                Match match10 = new Match();
                match10.setChampionship(championship);
                match10.setType(MatchType.GROUP_STAGE);
                match10.setStatus(MatchStatus.PROGRESS);
                match10.setHome_team(group.getTeams().get(1));
                match10.setAway_team(group.getTeams().get(2));
                match10.setJourney("Jornada 5");

                Match match11 = new Match();
                match11.setChampionship(championship);
                match11.setType(MatchType.GROUP_STAGE);
                match11.setStatus(MatchStatus.PROGRESS);
                match11.setHome_team(group.getTeams().get(2));
                match11.setAway_team(group.getTeams().get(0));
                match11.setJourney("Jornada 6");

                Match match12 = new Match();
                match12.setChampionship(championship);
                match12.setType(MatchType.GROUP_STAGE);
                match12.setStatus(MatchStatus.PROGRESS);
                match12.setHome_team(group.getTeams().get(3));
                match12.setAway_team(group.getTeams().get(1));
                match12.setJourney("Jornada 6");

                List<Match> matches = Arrays.asList(match1, match2, match3, match4, match5, match6, match7, match8, match9, match10, match11, match12);
                matchRepository.saveAll(matches);
            });
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void createKnockoutMatches(Long championshipId, int type){
        try {
            Optional<Championship> championship = championshipRepository.findById(championshipId);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            switch (type){
                case 1:
                    createQuarterFinal(championship.get());
                    break;
                case 2:
                    createSemiFinals(championship.get());
                    break;
                case 3:
                    createFinal(championship.get());
                    break;
                default:
                    throw new MessageBadRequestException("Tipo de jogos eliminatórios inválido");
            }
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    private void createQuarterFinal(Championship championship){
        try {
            Random random = new Random();
            List<Team> first_placed = new ArrayList<>();
            List<Team> second_placed = new ArrayList<>();
            Team first;
            Team second;
            int index;
            int size = 4;

            List<Match> matchesGroupStage = matchRepository.findMatchesByTypeAndStatusAndChampionshipId(championship.getId(), MatchType.GROUP_STAGE.ordinal(), MatchStatus.CLOSED.ordinal());
            if (matchesGroupStage.isEmpty() || matchesGroupStage.size() < 48)
                throw new MessageBadRequestException("Todos os jogos da fase de grupos devem estar encerrados");

            List<Group> groups = groupRepository.findGroupsByChampionshipId(championship.getId());
            if (groups.isEmpty())
                throw new MessageNotFoundException("Grupos não encontrados");

            groups.forEach(group -> {
                first_placed.add(group.getGroup_information().getFirst_place());
                second_placed.add(group.getGroup_information().getSecond_place());
            });

            while (!first_placed.isEmpty() && !second_placed.isEmpty()){
                Collections.shuffle(first_placed);
                Collections.shuffle(second_placed);

                index = random.nextInt(size);
                first = first_placed.get(index);
                first_placed.remove(index);

                index = random.nextInt(size);
                second = second_placed.get(index);
                second_placed.remove(index);

                Match match1 = new Match();
                match1.setChampionship(championship);
                match1.setType(MatchType.QUARTER_FINAL);
                match1.setStatus(MatchStatus.PROGRESS);
                match1.setHome_team(first);
                match1.setAway_team(second);

                matchRepository.save(match1);

                size -= 1;
            }
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    private void createSemiFinals(Championship championship){
        try {
            List<Team> champions = new ArrayList<>();

            List<Match> matchesQuarterFinal = matchRepository.findMatchesByTypeAndStatusAndChampionshipId(championship.getId(), MatchType.QUARTER_FINAL.ordinal(), MatchStatus.CLOSED.ordinal());
            if (matchesQuarterFinal.isEmpty() || matchesQuarterFinal.size() < 4)
                throw new MessageBadRequestException("Todos os jogos das quartas devem estar encerrados");

            matchesQuarterFinal.forEach(match -> {
                String[] scoreboard = match.getScoreboard().split("x");
                if (Integer.parseInt(scoreboard[0]) > Integer.parseInt(scoreboard[1]))
                    champions.add(match.getHome_team());
                else
                    champions.add(match.getAway_team());
            });

            Collections.shuffle(champions);

            Match match1 = new Match();
            match1.setChampionship(championship);
            match1.setType(MatchType.SEMIFINALS);
            match1.setStatus(MatchStatus.PROGRESS);
            match1.setHome_team(champions.get(0));
            match1.setAway_team(champions.get(1));

            Match match2 = new Match();
            match2.setChampionship(championship);
            match2.setType(MatchType.SEMIFINALS);
            match2.setStatus(MatchStatus.PROGRESS);
            match2.setHome_team(champions.get(2));
            match2.setAway_team(champions.get(3));

            List<Match> matches = Arrays.asList(match1, match2);
            matchRepository.saveAll(matches);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    private void createFinal(Championship championship){
        try {
            List<Team> champions = new ArrayList<>();

            List<Match> matchesSemiFinal = matchRepository.findMatchesByTypeAndStatusAndChampionshipId(championship.getId(), MatchType.SEMIFINALS.ordinal(), MatchStatus.CLOSED.ordinal());
            if (matchesSemiFinal.isEmpty() || matchesSemiFinal.size() < 2)
                throw new MessageBadRequestException("Todos os jogos das semis devem estar encerrados");

            matchesSemiFinal.forEach(match -> {
                String[] scoreboard = match.getScoreboard().split("x");
                if (Integer.parseInt(scoreboard[0]) > Integer.parseInt(scoreboard[1]))
                    champions.add(match.getHome_team());
                else
                    champions.add(match.getAway_team());
            });

            Match match1 = new Match();
            match1.setChampionship(championship);
            match1.setType(MatchType.FINAL);
            match1.setStatus(MatchStatus.PROGRESS);
            match1.setHome_team(champions.get(0));
            match1.setAway_team(champions.get(1));

            matchRepository.save(match1);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<Match> getMatchesBySearch(Long championship_id, String search) {
        try {
            Optional<Championship> championship = championshipRepository.findById(championship_id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            if (search.matches("[+-]?\\d*(\\.\\d+)?")){
                int param = Integer.parseInt(search);
                return matchRepository.findByTypeAndStatusSearch(championship.get().getId(), param);
            }else
                return matchRepository.findBySearch(championship.get().getId(), search);

        }
        catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Match> getMatchesByParams(Long id, String journey, int type) {
        try {
            if (!journey.isEmpty())
                return matchRepository.findByChampionshipIdAndJourneyAndType(id, journey, type);
            else
                return matchRepository.findByChampionshipIdAndType(id, type);
        }catch (Exception e){
            throw e;
        }
    }
}
