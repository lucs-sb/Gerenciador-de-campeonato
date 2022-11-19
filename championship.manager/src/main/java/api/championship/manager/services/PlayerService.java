package api.championship.manager.services;

import api.championship.manager.dtos.PlayerDTO;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Match;
import api.championship.manager.models.Player;
import api.championship.manager.models.Team;
import api.championship.manager.models.User;
import api.championship.manager.repositories.PlayerRepository;
import api.championship.manager.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void addPlayer(PlayerDTO newPlayer){
        try {
            Optional<Team> team = teamRepository.findById(newPlayer.getTeam().getId());
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não existe");

            Player player = new Player();
            player.setName(newPlayer.getName());
            player.setTeam(team.get());
            playerRepository.save(player);

            team.get().getPlayers().add(player);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public void updatePlayer(PlayerDTO newPlayer){
        try {
            Optional<Team> team = teamRepository.findById(newPlayer.getTeam().getId());
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não existe");

            Optional<Player> player = playerRepository.findById(newPlayer.getId());
            if (player.isEmpty())
                throw new MessageNotFoundException("Jogador não existe");

            player.get().setName(newPlayer.getName());
            player.get().setTeam(team.get());
            playerRepository.save(player.get());
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public void deletePlayer(Long id){
        try {
            Optional<Player> player = playerRepository.findById(id);
            if (player.isEmpty())
                throw new MessageNotFoundException("Jogador não existe");

            player.get().getTeam().getPlayers().remove(player.get());

            playerRepository.delete(player.get());
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public Page<Player> getAllPlayersByTeam(Long team_id, Pageable pageable) {
        try {
            Optional<Team> team = teamRepository.findById(team_id);
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não encontrado");

            return playerRepository.findByTeamId(team.get().getId(), pageable);
        }catch (Exception e){
            throw e;
        }
    }
}
