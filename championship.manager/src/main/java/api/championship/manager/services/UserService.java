package api.championship.manager.services;

import api.championship.manager.dtos.UserDTO;
import api.championship.manager.execeptionHandler.exceptions.MessageBadRequestException;
import api.championship.manager.execeptionHandler.exceptions.MessageNotFoundException;
import api.championship.manager.models.Login;
import api.championship.manager.models.Role;
import api.championship.manager.models.User;
import api.championship.manager.repositories.LoginRepository;
import api.championship.manager.repositories.RoleRepository;
import api.championship.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        try {
            Optional<User> user = repository.findById(id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");
            return user;
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void addUser(UserDTO newUser){
        try {
            Optional<User> user = repository.findByEmail(newUser.getEmail());
            if (user.isPresent())
                throw new MessageBadRequestException("Email já cadastrado");

            user = Optional.of(new User());
            user.get().setName(newUser.getName());
            user.get().setEmail(newUser.getEmail());
            repository.saveAndFlush(user.get());

            Login login = new Login();
            login.setEmail(newUser.getEmail());
            login.setPassword(passwordEncoder.encode(newUser.getPassword()));
            login.setRoles(getRoles());
            login.setUser(user.get());
            loginRepository.saveAndFlush(login);

            user.get().setLogin(login);
            repository.save(user.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void updateUserById(Long id, UserDTO updateUser){
        try {
            Optional<User> user = repository.findById(id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            Optional<Login> login = loginRepository.findById(user.get().getLogin().getId());

            if (!updateUser.getName().isEmpty())
                 user.get().setName(updateUser.getName());

            if (!updateUser.getEmail().isEmpty()){
                user.get().setEmail(updateUser.getEmail());
                login.get().setEmail(updateUser.getEmail());
            }

            if (!updateUser.getPassword().isEmpty())
                login.get().setPassword(updateUser.getPassword());

            user.get().setLogin(login.get());

            repository.save(user.get());
            loginRepository.save(login.get());

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteUserById(Long id){
        try {
            Optional<User> user = repository.findById(id);
            if (user.isEmpty())
                throw new MessageNotFoundException("Usuário não encontrado");

            repository.delete(user.get());
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    private List<Role> getRoles(){
        try {
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findByName("USER");
            roles.add(role);
            return roles;
        }catch (Exception e){
            throw e;
        }
    }
}
