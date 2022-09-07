package api.championship.manager.controllers;

import api.championship.manager.dtos.UserDTO;
import api.championship.manager.models.User;
import api.championship.manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) throws Exception{
        try {
            Optional<User> user = service.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserDTO newUser) throws Exception{
        try {
            service.addUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO newUser) throws Exception{
        try {
            service.updateUserById(id, newUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity deleteUser(@PathVariable Long id) throws Exception{
        try {
            service.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
