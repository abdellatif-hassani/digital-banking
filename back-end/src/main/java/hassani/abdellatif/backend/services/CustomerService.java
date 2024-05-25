package hassani.abdellatif.backend.services;

import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.entities.Customer;
import hassani.abdellatif.backend.exceptions.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> listCustomers();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<CustomerDTO> searchCustomers(String keyword);

    Customer findByEmail(String email);
}
