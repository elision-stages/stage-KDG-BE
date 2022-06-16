package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for users
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a users by email
     *
     * @param email the email of the user that you want to find
     * @return the user with given email
     */
    User findByEmail(String email);
}