package api.championship.manager.controllers;

import api.championship.manager.models.Event;
import api.championship.manager.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/match/{id}")
    public ResponseEntity<List<Event>> getEvents(@PathVariable Long id) throws Exception{
        try {
            List<Event> events = eventService.getEventsByMatch(id);
            return new ResponseEntity<>(events, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping
    public ResponseEntity addEvent(@RequestBody Event event) throws Exception{
        try {
            eventService.addEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Long id, @RequestBody Event event) throws Exception{
        try {
            eventService.updateEvent(id, event);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable Long id) throws Exception{
        try {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}