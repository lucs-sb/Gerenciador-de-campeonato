package api.championship.manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_team")
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shield_img;
    private String abbreviation;
    @JsonIgnore
    @ManyToMany(mappedBy = "teams")
    private List<Championship> championships;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Team(){}
}
