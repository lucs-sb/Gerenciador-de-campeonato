package api.championship.manager.services;

import api.championship.manager.enums.ChampionshipStatus;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Team;
import api.championship.manager.models.User;
import api.championship.manager.repositories.ChampionshipRepository;
import api.championship.manager.repositories.TeamRepository;
import api.championship.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChampionshipService {
    @Autowired
    private ChampionshipRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Page<Championship> getAllChampionships(Long user_id, Pageable pageable){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            return repository.findByUserIdAndDeletionDateIsNull(user.get().getId(), pageable);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void addChampionship(Long user_id, Championship newChampionship){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            List<Team> teamList = new ArrayList<>();
            newChampionship.getTeams().forEach(t -> {
                Optional<Team> team = teamRepository.findByIdAndUserId(t.getId(), user.get().getId());
                if (team.isPresent())
                    teamList.add(team.get());
                else
                    throw new MessageNotFoundException("Time não cadastrado. Id: "+t.getId());
            });

            Championship championship = new Championship();
            championship.setUser(user.get());
            championship.setName(newChampionship.getName());
            championship.setDescription(newChampionship.getDescription());
            championship.setAward(newChampionship.getAward());
            championship.setStatus(ChampionshipStatus.IN_PROGRESS);
            championship.setTeams(teamList);
            repository.saveAndFlush(championship);

            championship.setGroups(groupService.groupDraw(championship));

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Championship> getChampionshipById(Long id) {
        try {
            Optional<Championship> championship = repository.findById(id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            return championship;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateChampionship(Championship newChampionship, Long id) {
        try {
            Optional<Championship> championship = repository.findById(id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            championship.get().setName(newChampionship.getName());
            championship.get().setDescription(newChampionship.getDescription());
            championship.get().setAward(newChampionship.getAward());
            championship.get().setStatus(newChampionship.getStatus());
            repository.save(championship.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteChampionship(Long id) {
        try {
            Optional<Championship> championship = repository.findById(id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            championship.get().setDeletionDate(LocalDateTime.now());

            repository.save(championship.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Page<Championship> getChampionshipsBySearch(Long user_id, String search, Pageable pageable) {
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            if (search.isEmpty())
                return repository.findByUserIdAndDeletionDateIsNull(user.get().getId(), pageable);

            if (search.matches("[+-]?\\d*(\\.\\d+)?")){
                int param = Integer.parseInt(search);
                return repository.findByparams(user.get().getId(), param, pageable);
            }
            else
                return repository.findByUserAndName(user.get().getId(), search, pageable);
        }catch (Exception e){
            throw e;
        }
    }
}
