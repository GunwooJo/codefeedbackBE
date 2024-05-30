package cloudweb.codefeedbackBE.repository;

import cloudweb.codefeedbackBE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("delete from User u where u.email = :email")
    public void deleteUserByEmail(String email);

    public User userSignIn(HashMap<String, String> loginUser);

}
