package hassani.abdellatif.backend.web;

import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.entities.AuthEntity;
import hassani.abdellatif.backend.entities.Customer;
import hassani.abdellatif.backend.jwt.JwtResponse;
import hassani.abdellatif.backend.jwt.JwtUtil;
import hassani.abdellatif.backend.repositories.AuthRepository;
import hassani.abdellatif.backend.services.AuthService;
import hassani.abdellatif.backend.services.CustomerService;
import hassani.abdellatif.backend.utils.AuthRequest;
import hassani.abdellatif.backend.utils.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final CustomerService customerService;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public CustomerDTO signUp(@RequestBody SignUpRequest signUpRequest) {
        CustomerDTO savedCustomer = authService.signUp(signUpRequest);
        return savedCustomer;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest authRequest) {
        return authService.signIn(authRequest);
    }
}
