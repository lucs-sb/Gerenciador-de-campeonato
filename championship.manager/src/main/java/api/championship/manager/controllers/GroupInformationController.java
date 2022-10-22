package api.championship.manager.controllers;

import api.championship.manager.models.GroupInformation;
import api.championship.manager.services.GroupInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/group_information")
public class GroupInformationController {
    @Autowired
    private GroupInformationService groupInformationService;

    @PutMapping
    public ResponseEntity updateGroupInformation(@RequestBody GroupInformation groupInformation) throws Exception{
        try {
            groupInformationService.updateGroupInformation(groupInformation);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }
}
