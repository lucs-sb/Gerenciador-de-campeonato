package api.championship.manager.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", locale = "pt-br")
    private Date time;
    private String description;
    private String value;

    public Event(){}
}
