package recipes.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import recipes.models.User;

import java.util.Optional;

@Service
public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
