package api.championship.manager.controllers;

import api.championship.manager.models.Team;
import api.championship.manager.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService service;

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Page<Team>> getAll(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long user_id) throws Exception{
        try {
            Page<Team> teams = service.getAllTeams(user_id, pageable);
            return new ResponseEntity<>(teams, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Optional<Team>> getById(@PathVariable Long id) throws Exception{
        try {
            Optional<Team> team = service.getTeamById(id);
            return new ResponseEntity<>(team, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping(value = "/user/{user_id}/search")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Team>> getBySearch(@PathVariable Long user_id, @RequestParam("search") String search)
            throws Exception {
        try {
            List<Team> teams = service.getTeamsBySearch(user_id, search);
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/user/{user_id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity add(@RequestBody Team team, @PathVariable Long user_id) throws Exception{
        try {
            service.addTeam(user_id, team);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity update(@RequestBody Team team, @PathVariable Long id) throws Exception{
        try {
            service.updateTeam(id, team);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws Exception{
        try {
            service.deleteTeam(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}