package api.championship.manager.services;

import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.GroupInformation;
import api.championship.manager.models.Team;
import api.championship.manager.repositories.GroupInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupInformationService {
    @Autowired
    private GroupInformationRepository groupInformationRepository;

    public GroupInformation createGroupInformation(List<Team> teams){
        try {
            GroupInformation groupInformation = new GroupInformation();
            groupInformation.setFirst_place(teams.get(0));
            groupInformation.setSecond_place(teams.get(1));
            groupInformation.setThird_place(teams.get(2));
            groupInformation.setFourth_place(teams.get(3));

            groupInformationRepository.save(groupInformation);

            return groupInformation;
        }catch (Exception ex){
            throw ex;
        }
    }

    public void updateGroupInformation(GroupInformation newGroupInformation){
        try {
            Optional<GroupInformation> groupInformation = groupInformationRepository.findById(newGroupInformation.getId());
            if (groupInformation.isEmpty())
                throw new MessageNotFoundException("Grupo não encontrado");

            groupInformation.get().setFirst_place(newGroupInformation.getFirst_place());
            groupInformation.get().setSecond_place(newGroupInformation.getSecond_place());
            groupInformation.get().setThird_place(newGroupInformation.getThird_place());
            groupInformation.get().setFourth_place(newGroupInformation.getFourth_place());
            groupInformation.get().setFirst_place_points(newGroupInformation.getFirst_place_points());
            groupInformation.get().setSecond_place_points(newGroupInformation.getSecond_place_points());
            groupInformation.get().setThird_place_points(newGroupInformation.getThird_place_points());
            groupInformation.get().setFourth_place_points(newGroupInformation.getFourth_place_points());

            groupInformationRepository.save(groupInformation.get());
        }catch (Exception ex){
            throw ex;
        }
    }
}
