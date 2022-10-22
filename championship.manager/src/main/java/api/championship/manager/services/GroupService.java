package api.championship.manager.services;

import api.championship.manager.enums.GroupName;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Group;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MatchService matchService;
    @Autowired
    private GroupInformationService groupInformationService;

    public List<Group> getGroupsByChampionshipId(Long championshipId){
        try {
            return groupRepository.findGroupsByChampionshipId(championshipId);
        }catch (Exception ex){
            throw ex;
        }
    }

    public void updateGroup(Group newGroup){
        try {
            Optional<Group> group = groupRepository.findById(newGroup.getId());
            if (group.isEmpty())
                throw new MessageNotFoundException("Grupo não encontrado");

            group = Optional.of(new Group());
            group.get().setTeams(newGroup.getTeams());
            groupRepository.save(group.get());
        }catch (Exception ex){
            throw ex;
        }
    }

    public void groupDraw(Championship championship){
        try {
            Random random = new Random();
            Group a = new Group();
            Group b = new Group();
            Group c = new Group();
            Group d = new Group();
            List<Team> teams = championship.getTeams();
            List<Team> group_A = new ArrayList<>();
            List<Team> group_B = new ArrayList<>();
            List<Team> group_C = new ArrayList<>();
            List<Team> group_D = new ArrayList<>();
            int size = 16;
            int index;

            while (!teams.isEmpty()){
                Collections.shuffle(teams);
                index = random.nextInt(size);
                if (group_A.size() < 4) {
                    group_A.add(teams.get(index));
                    teams.remove(index);
                }
                else if (group_B.size() < 4){
                    group_B.add(teams.get(index));
                    teams.remove(index);
                }
                else if (group_C.size() < 4){
                    group_C.add(teams.get(index));
                    teams.remove(index);
                }
                else{
                    group_D.add(teams.get(index));
                    teams.remove(index);
                }

                size -= 1;
            }

            a.setName_group(GroupName.A);
            a.setChampionship(championship);
            a.setTeams(group_A);
            a.setGroup_information(groupInformationService.createGroupInformation(group_A));

            b.setName_group(GroupName.B);
            b.setChampionship(championship);
            b.setTeams(group_B);
            b.setGroup_information(groupInformationService.createGroupInformation(group_B));

            c.setName_group(GroupName.C);
            c.setChampionship(championship);
            c.setTeams(group_C);
            c.setGroup_information(groupInformationService.createGroupInformation(group_C));

            d.setName_group(GroupName.D);
            d.setChampionship(championship);
            d.setTeams(group_D);
            d.setGroup_information(groupInformationService.createGroupInformation(group_D));

            List<Group> groups = Arrays.asList(a, b, c, d);
            groupRepository.saveAll(groups);

            matchService.createMatches(championship, groups);

        }catch (Exception ex){
            throw ex;
        }
    }
}
