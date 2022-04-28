package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
}