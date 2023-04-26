package ecom.service;

import ecom.domain.Address;
import ecom.domain.Customer;
import ecom.domain.User;
import ecom.dto.UserDto;
import ecom.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;

    private CustomerService customerService;

    private AddressService addressService;

    public UserService(UserRepository userRepository, CustomerService customerService,AddressService addressService) {
        this.userRepository = userRepository;
        this.customerService = customerService;
        this.addressService = addressService;
    }

    public List<User> list() {
        return userRepository.list();
    }

    public User get(Long id) {
        return userRepository.get(id);
    }

    public boolean create(User user) {
        return userRepository.create(user);
    }

    public boolean create(UserDto userDto) {



        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        user.setEnabled(true);
        user.setCreatedOn(LocalDate.now());
        userRepository.create(user);

        Customer customer = new Customer();
        customer.setGender(userDto.getGender());
        customer.setDateOfBirth(userDto.getDateOfBirth());
        customer.setUser(user);

        System.out.println(user);
        customerService.create(customer);

        Address address = new Address();
        address.setAddress(userDto.getAddress());
        address.setAddressType(userDto.getAddressType());

        address.setCustomer(customer);
        System.out.println(userDto.getAddress());
        System.out.println(userDto.getAddressType());
        System.out.println(customer);

        return  addressService.create(address);


    }

    public boolean update(User user) {
        return userRepository.update(user);
    }

    public boolean delete(Long id) {
        return userRepository.delete(get(id));
    }
}
