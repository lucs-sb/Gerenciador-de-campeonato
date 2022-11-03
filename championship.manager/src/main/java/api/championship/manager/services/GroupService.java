package api.championship.manager.services;

import api.championship.manager.dtos.GroupDTO;
import api.championship.manager.enums.GroupName;
import api.championship.manager.execeptionHandler.exceptions.MessageBadRequestException;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Championship;
import api.championship.manager.models.Group;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.ChampionshipRepository;
import api.championship.manager.repositories.GroupRepository;
import api.championship.manager.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MatchService matchService;
    @Autowired
    private GroupInformationService groupInformationService;
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public List<Group> getGroupsByChampionshipId(Long championshipId){
        try {
            return groupRepository.findGroupsByChampionshipId(championshipId);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public void updateGroup(GroupDTO newGroup){
        try {
            Optional<Group> group = groupRepository.findById(newGroup.getId());
            if (group.isEmpty())
                throw new MessageNotFoundException("Grupo não encontrado");

            Optional<Championship> championship = championshipRepository.findById(newGroup.getChampionship().getId());
            if (championship.isEmpty())
                throw new MessageBadRequestException("Campeonato não encontrado");

            List<Team> teamList = new ArrayList<>();
            newGroup.getTeams().forEach(t -> {
                Optional<Team> team = teamRepository.findByIdAndUserId(t.getId(), championship.get().getUser().getId());
                if (team.isPresent())
                    teamList.add(team.get());
                else
                    throw new MessageNotFoundException("Time não cadastrado. Id: "+t.getId());
            });

            group.get().setTeams(teamList);
            group.get().setGroup_information(groupInformationService.updateGroupInformationForGroup(newGroup.getGroup_information()));
            groupRepository.save(group.get());

        }catch (Exception ex){
            throw ex;
        }
    }

    @Transactional
    public List<Group> groupDraw(Championship championship){
        try {
            Random random = new Random();
            Group a = new Group();
            Group b = new Group();
            Group c = new Group();
            Group d = new Group();
            List<Team> teams = new ArrayList<>(championship.getTeams());
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

            matchService.createMatches(championship, groups);

            return new ArrayList<>(groups);
        }catch (Exception ex){
            throw ex;
        }
    }
}
