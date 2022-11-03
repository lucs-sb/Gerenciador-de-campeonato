package api.championship.manager.dtos;

import api.championship.manager.enums.GroupName;
import api.championship.manager.models.Championship;
import api.championship.manager.models.GroupInformation;
import api.championship.manager.models.Team;
import lombok.Getter;

import java.util.List;

@Getter
public class GroupDTO {
    private Long id;
    private GroupName name_group;
    private Championship championship;
    private List<Team> teams;
    private GroupInformation group_information;
}
