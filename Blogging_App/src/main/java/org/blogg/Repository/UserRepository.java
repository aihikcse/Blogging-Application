package org.blogg.Repository;

import java.util.Optional;

import org.blogg.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
