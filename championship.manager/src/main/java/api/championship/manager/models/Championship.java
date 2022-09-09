package api.championship.manager.models;

import api.championship.manager.enums.ChampionshipConfiguration;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private ChampionshipConfiguration championship_configuration;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Championship(){}
}
