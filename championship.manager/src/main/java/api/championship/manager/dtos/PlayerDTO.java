package api.championship.manager.dtos;

import api.championship.manager.models.Team;
import lombok.Getter;

@Getter
public class PlayerDTO {
    private Long id;
    private String name;
    private Team team;
}
