package hassani.abdellatif.backend.services;

import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.utils.AuthRequest;
import hassani.abdellatif.backend.utils.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    CustomerDTO signUp(SignUpRequest signUpRequest);
    ResponseEntity<?> signIn(AuthRequest authRequest);
}
