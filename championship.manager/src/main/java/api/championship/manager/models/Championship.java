package api.championship.manager.models;

import api.championship.manager.enums.ChampionshipStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_championship")
@Getter
@Setter
public class Championship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int number_of_teams;
    private String award;
    private ChampionshipStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(
            name = "championship_team",
            joinColumns = @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "championship_group",
            joinColumns = @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;
    @JsonIgnore
    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL)
    private List<Match> Matches;

    public Championship(){}
}
