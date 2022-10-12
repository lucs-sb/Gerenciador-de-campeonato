package api.championship.manager.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_match")
@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
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

    public Match(){}
}
