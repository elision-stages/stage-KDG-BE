package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.users.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa repository for addresses
 */
public interface AddressRepository extends JpaRepository<Address, Long>
{
}