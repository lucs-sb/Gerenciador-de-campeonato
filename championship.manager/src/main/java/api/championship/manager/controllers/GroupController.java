package api.championship.manager.controllers;

import api.championship.manager.dtos.GroupDTO;
import api.championship.manager.models.Group;
import api.championship.manager.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/championship/{championshipId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Group>> getGroups(@PathVariable Long championshipId) throws Exception{
        try {
            List<Group> groups = groupService.getGroupsByChampionshipId(championshipId);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity updateGroup(@RequestBody GroupDTO group) throws Exception{
        try {
            groupService.updateGroup(group);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }
}
