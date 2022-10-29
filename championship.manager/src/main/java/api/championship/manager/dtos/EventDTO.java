package api.championship.manager.dtos;

import api.championship.manager.models.Match;
import api.championship.manager.models.Player;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.util.Date;

@Getter
public class EventDTO {
    private Long id;
    private Match match;
    private Player player;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", locale = "pt-br")
    private Date time;
    private String description;
    private String value;
}
