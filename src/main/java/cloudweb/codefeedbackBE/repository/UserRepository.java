package cloudweb.codefeedbackBE.repository;

import cloudweb.codefeedbackBE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
}
