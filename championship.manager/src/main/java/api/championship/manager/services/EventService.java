package api.championship.manager.services;

import api.championship.manager.dtos.EventDTO;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Event;
import api.championship.manager.models.Match;
import api.championship.manager.models.Player;
import api.championship.manager.repositories.EventRepository;
import api.championship.manager.repositories.MatchRepository;
import api.championship.manager.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<Event> getEventsByMatch(Long id) {
        try {
            Optional<Match> match = matchRepository.findById(id);
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            return eventRepository.findByMatchId(match.get().getId());
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void addEvent(EventDTO newEvent) {
        try {
            Optional<Match> match = matchRepository.findById(newEvent.getMatch().getId());
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            Event event = new Event();

            if (newEvent.getPlayer() != null) {
                Optional<Player> player = playerRepository.findById(newEvent.getPlayer().getId());
                if (player.isEmpty())
                    throw new MessageNotFoundException("Jogador não encontrado");

                event.setDescription(newEvent.getDescription());
                event.setTime(newEvent.getTime());
                event.setMatch(match.get());
                event.setPlayer(player.get());
            }
            else {
                event.setDescription(newEvent.getDescription());
                event.setTime(newEvent.getTime());
                event.setValue(newEvent.getValue());
                event.setMatch(match.get());
            }

            eventRepository.save(event);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateEvent(Long id, EventDTO newEvent) {
        try {
            Optional<Event> event = eventRepository.findById(id);
            if (event.isEmpty())
                throw new MessageNotFoundException("Evento não encontrado");

            Optional<Match> match = matchRepository.findById(newEvent.getMatch().getId());
            if (match.isEmpty())
                throw new MessageNotFoundException("Partida não encontrada");

            if (newEvent.getPlayer() != null) {
                Optional<Player> player = playerRepository.findById(newEvent.getPlayer().getId());
                if (player.isEmpty())
                    throw new MessageNotFoundException("Jogador não encontrado");

                event.get().setDescription(newEvent.getDescription());
                event.get().setTime(newEvent.getTime());
                event.get().setMatch(match.get());
                event.get().setPlayer(player.get());
            }
            else {
                event.get().setDescription(newEvent.getDescription());
                event.get().setTime(newEvent.getTime());
                event.get().setValue(newEvent.getValue());
                event.get().setMatch(match.get());
            }

            eventRepository.save(event.get());
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteEvent(Long id) {
        try {
            Optional<Event> event = eventRepository.findById(id);
            if (event.isEmpty())
                throw new MessageNotFoundException("Evento não encontrado");

            event.get().getMatch().getEvents().remove(event.get());

            eventRepository.delete(event.get());
        }catch (Exception e){
            throw e;
        }
    }
}
