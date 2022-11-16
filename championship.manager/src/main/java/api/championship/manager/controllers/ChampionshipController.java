package api.championship.manager.controllers;

import api.championship.manager.models.Championship;
import api.championship.manager.services.ChampionshipService;
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
@RequestMapping("/api/championship")
public class ChampionshipController {
    @Autowired
    private ChampionshipService service;

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Page<Championship>> getAll(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long user_id) throws Exception{
        try {
            Page<Championship> championships = service.getAllChampionships(user_id, pageable);
            return new ResponseEntity<>(championships, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Optional<Championship>> getById(@PathVariable Long id) throws Exception{
        try {
            Optional<Championship> championship = service.getChampionshipById(id);
            return new ResponseEntity<>(championship, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping(value = "/user/{user_id}/search")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Championship>> getBySearch(@PathVariable Long user_id, @RequestParam("search") String search, @RequestParam("ordination") String ordination)
            throws Exception {
        try {
            List<Championship> championships = service.getChampionshipsBySearch(user_id, search, ordination);
            return new ResponseEntity<>(championships, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/user/{user_id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity add(@RequestBody Championship championship, @PathVariable Long user_id) throws Exception{
        try {
            service.addChampionship(user_id, championship);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity update(@RequestBody Championship championship, @PathVariable Long id) throws Exception{
        try {
            service.updateChampionship(championship, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) throws Exception{
        try {
            service.deleteChampionship(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
