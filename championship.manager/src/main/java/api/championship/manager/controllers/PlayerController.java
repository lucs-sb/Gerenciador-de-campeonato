package api.championship.manager.controllers;

import api.championship.manager.models.Player;
import api.championship.manager.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping
    public ResponseEntity addPlayer(@RequestBody Player player) throws Exception{
        try {
            playerService.addPlayer(player);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    @PutMapping
    public ResponseEntity updatePlayer(@RequestBody Player player) throws Exception{
        try {
            playerService.updatePlayer(player);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlayer(@PathVariable Long id) throws Exception{
        try {
            playerService.deletePlayer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }
}
