package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Player;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.PlayerRepository;
import api.championship.manager.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addPlayer(Player newPlayer){
        try {
            Optional<Team> team = teamRepository.findById(newPlayer.getTeam().getId());
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não existe");

            Player player = new Player();
            player.setName(newPlayer.getName());
            player.setTeam(team.get());
            playerRepository.save(player);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public void updatePlayer(Player newPlayer){
        try {
            Optional<Team> team = teamRepository.findById(newPlayer.getTeam().getId());
            if (team.isEmpty())
                throw new MessageNotFoundException("Time não existe");

            Optional<Player> player = playerRepository.findById(newPlayer.getId());
            if (player.isEmpty())
                throw new MessageNotFoundException("Jogador não existe");

            player = Optional.of(new Player());
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

            playerRepository.delete(player.get());
        }catch (Exception ex){
            throw ex;
        }
    }
}
