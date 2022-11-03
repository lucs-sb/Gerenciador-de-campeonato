package api.championship.manager.controllers;

import api.championship.manager.dtos.EventDTO;
import api.championship.manager.models.Event;
import api.championship.manager.services.EventService;
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

@Controller
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/match/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Page<Event>> getEvents(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id) throws Exception{
        try {
            Page<Event> events = eventService.getEventsByMatch(pageable, id);
            return new ResponseEntity<>(events, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity addEvent(@RequestBody EventDTO event) throws Exception{
        try {
            eventService.addEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity updateEvent(@PathVariable Long id, @RequestBody EventDTO event) throws Exception{
        try {
            eventService.updateEvent(id, event);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity deleteEvent(@PathVariable Long id) throws Exception{
        try {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping(value = "/match/{match_id}/search")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Event>> getBySearch(@PathVariable Long match_id, @RequestParam("search") String search)
            throws Exception {
        try {
            List<Event> events = eventService.getEventsBySearch(match_id, search);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}