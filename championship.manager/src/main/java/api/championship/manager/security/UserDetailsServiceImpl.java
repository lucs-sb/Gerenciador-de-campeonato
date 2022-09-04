package api.championship.manager.security;

import api.championship.manager.execeptionHandler.MessageNotFoundException;
import api.championship.manager.models.Login;
import api.championship.manager.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Login> login = loginRepository.findByEmail(email);
        if (login.isEmpty())
            throw new MessageNotFoundException("Usuário não encontrado");

        return new User(login.get().getUser().getId().toString(), login.get().getPassword(), true, true, true, true, login.get().getAuthorities());
    }
}
