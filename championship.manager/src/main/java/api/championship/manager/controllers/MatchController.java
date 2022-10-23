package api.championship.manager.controllers;

import api.championship.manager.enums.MatchType;
import api.championship.manager.models.Match;
import api.championship.manager.services.MatchService;
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

@Controller
@RequestMapping("/api/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping("/championship/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Page<Match>> getMatches(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id) throws Exception{
        try {
            Page<Match> matches = matchService.getMatches(id, pageable);
            return new ResponseEntity<>(matches, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) throws Exception{
        try {
            Match match = matchService.getMatchById(id);
            return new ResponseEntity<>(match, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity addMatch(@RequestBody Match match) throws Exception{
        try {
            matchService.addMatch(match);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/championship/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity createKnockoutMatches(@PathVariable Long id, @RequestParam MatchType type) throws Exception{
        try {
            matchService.createKnockoutMatches(id, type);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity updateMatch(@PathVariable Long id, @RequestBody Match match) throws Exception{
        try {
            matchService.updateMatch(id, match);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity deleteMatch(@PathVariable Long id) throws Exception{
        try {
            matchService.deleteMatch(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}