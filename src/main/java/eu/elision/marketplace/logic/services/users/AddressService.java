package eu.elision.marketplace.logic.services.users;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for addresses
 */
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    /**
     * Public constructor
     *
     * @param addressRepository autowired repository
     */
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Find all of the addresses in the repository
     *
     * @return a list with all of the addresses
     */
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address findById(long id) {
        return addressRepository.findById(id).orElse(null);
    }
}
