package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Team;
import api.championship.manager.models.User;
import api.championship.manager.repositories.ChampionshipRepository;
import api.championship.manager.repositories.PlayerRepository;
import api.championship.manager.repositories.TeamRepository;
import api.championship.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Page<Team> getAllTeams(Long user_id, Pageable pageable){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            return repository.findByUserId(user.get().getId(), pageable);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Team> getTeamById(Long id){
        try {
            Optional<Team> team = repository.findById(id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            return team;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Page<Team> getTeamsBySearch(Long user_id, String search, Pageable pageable){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            if (search.isEmpty())
                return repository.findByUserId(user.get().getId(), pageable);

            return repository.findByNameOrAbbreviation(user_id, search, pageable);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeamsThatAreNotInTheChampionship(Long user_id, Long championship_id){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            List<Team> teams = repository.findAll();
            List<Team> championship_teams = repository.findByUserIdAndChampionshipId(user_id, championship_id);

            championship_teams.forEach(team -> {
                teams.removeIf(team1 -> team1.getId().equals(team.getId()));
            });

            return teams;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public void addTeamInChampionship(Long championship_id, List<Long> teams_ids){
        try {
            Optional<Championship> championship = championshipRepository.findById(championship_id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            teams_ids.forEach(id -> {
            Optional<Team> team = repository.findById(id);
            team.ifPresent(championship.get().getTeams()::add);
            team.ifPresent(value -> value.getChampionships().add(championship.get()));
            });
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void addTeam(Long user_id, Team newTeam){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            Team team = new Team();
            team.setName(newTeam.getName());
            team.setAbbreviation(newTeam.getAbbreviation());
            team.setShield_img(newTeam.getShield_img());
            team.setUser(user.get());
            repository.save(team);

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateTeam(Long id, Team newTeam){
        try {
            Optional<Team> team = repository.findById(id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            team.get().setName(newTeam.getName());
            team.get().setAbbreviation(newTeam.getAbbreviation());
            team.get().setShield_img(newTeam.getShield_img());
            repository.save(team.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteTeam(Long id){
        try {
            Optional<Team> team = repository.findById(id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            team.get().getChampionships().forEach(c -> {c.getTeams().remove(team.get());});

            repository.delete(team.get());
        }catch (Exception e){
            throw e;
        }
    }
}
