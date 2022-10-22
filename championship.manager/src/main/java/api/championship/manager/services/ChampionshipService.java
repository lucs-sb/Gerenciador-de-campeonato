package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.User;
import api.championship.manager.repositories.ChampionshipRepository;
import api.championship.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChampionshipService {
    @Autowired
    private ChampionshipRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupService groupService;

    public Page<Championship> getAllChampionships(Long user_id, Pageable pageable){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            return repository.findByUserId(user.get().getId(), pageable);
        }catch (Exception e){
            throw e;
        }
    }

    public void addChampionship(Long user_id, Championship newChampionship){
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            Championship championship = new Championship();
            championship.setUser(user.get());
            championship.setName(newChampionship.getName());
            championship.setDescription(newChampionship.getDescription());
            championship.setAward(newChampionship.getAward());
            championship.setNumber_of_teams(newChampionship.getNumber_of_teams());
            championship.setStatus(newChampionship.getStatus());
            championship.setTeams(newChampionship.getTeams());
            repository.save(championship);

            groupService.groupDraw(championship);
        }catch (Exception e){
            throw e;
        }
    }

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

    public void updateChampionship(Championship newChampionship, Long id) {
        try {
            Optional<Championship> championship = repository.findById(id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            championship.get().setName(newChampionship.getName());
            championship.get().setDescription(newChampionship.getDescription());
            championship.get().setAward(newChampionship.getAward());
            championship.get().setNumber_of_teams(newChampionship.getNumber_of_teams());
            championship.get().setStatus(newChampionship.getStatus());
            repository.save(championship.get());

        }catch (Exception e){
            throw e;
        }
    }

    public void deleteChampionship(Long id) {
        try {
            Optional<Championship> championship = repository.findById(id);
            if (championship.isEmpty())
                throw new MessageNotFoundException("Campeonato não encontrado");

            repository.delete(championship.get());

        }catch (Exception e){
            throw e;
        }
    }

    public List<Championship> getChampionshipsBySearch(Long user_id, String search) {
        try {
            Optional<User> user = userRepository.findById(user_id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            if (search.matches("[+-]?\\d*(\\.\\d+)?")){
                int param = Integer.parseInt(search);
                return repository.findByparams(user.get().getId(), param);
            }
            else
                return repository.findByUserAndName(user.get().getId(), search);
        }catch (Exception e){
            throw e;
        }
    }
}
