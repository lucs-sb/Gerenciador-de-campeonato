package api.championship.manager.models;

import api.championship.manager.enums.MatchStatus;
import api.championship.manager.enums.MatchType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_match")
@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;
    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team away_team;
    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team home_team;
    private LocalDateTime date;
    private LocalDateTime time;
    private String place;
    private String scoreboard;
    private MatchStatus status;
    private MatchType type;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Event> events;

    public Match(){}
}
