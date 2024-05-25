package hassani.abdellatif.backend.services;

import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.entities.AuthEntity;
import hassani.abdellatif.backend.entities.Customer;
import hassani.abdellatif.backend.jwt.JwtResponse;
import hassani.abdellatif.backend.jwt.JwtUtil;
import hassani.abdellatif.backend.repositories.AuthRepository;
import hassani.abdellatif.backend.utils.AuthRequest;
import hassani.abdellatif.backend.utils.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AutheServiceImpl implements AuthService{
    private final CustomerService customerService;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;
     public CustomerDTO signUp(SignUpRequest signUpRequest) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(signUpRequest.getName());
        customerDTO.setEmail(signUpRequest.getEmail());
        CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setEmail(signUpRequest.getEmail());
        authEntity.setPassword(signUpRequest.getPassword());  // Consider encoding the password
        this.authRepository.save(authEntity);

        return savedCustomer;
    }

    @Override
    public ResponseEntity<?> signIn(AuthRequest authRequest) {
        AuthEntity authEntity = authRepository.findByEmail(authRequest.getEmail());
        if (authEntity == null || !authRequest.getPassword().equals(authEntity.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        Customer customer = customerService.findByEmail(authRequest.getEmail());
        if (customer == null) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        String jwt = jwtUtil.generateToken(authRequest.getEmail(), customer.getId());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


}
