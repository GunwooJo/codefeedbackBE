package cloudweb.codefeedbackBE.repository;

import cloudweb.codefeedbackBE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("delete from User u where u.email = :email")
    public void deleteUserByEmail(String email);

    @Query("select u from User u where u.email = :email and u.password = :password")
    public User userSignIn(String email, String password);

}
