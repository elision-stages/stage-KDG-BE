package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService
{
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll()
    {
        return addressRepository.findAll();
    }

    public Address save(Address address)
    {
        return addressRepository.save(address);
    }

    public Address findById(long id)
    {
        return addressRepository.findById(id).orElse(null);
    }
}
