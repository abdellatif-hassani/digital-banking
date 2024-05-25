package hassani.abdellatif.backend.security;

import hassani.abdellatif.backend.entities.AuthEntity;
import hassani.abdellatif.backend.repositories.AuthRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public CustomUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthEntity authEntity = authRepository.findByEmail(email);
        if (authEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(authEntity.getEmail(), authEntity.getPassword(), new ArrayList<>());
    }
}

