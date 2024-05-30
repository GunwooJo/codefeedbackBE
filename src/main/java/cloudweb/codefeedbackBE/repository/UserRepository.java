package cloudweb.codefeedbackBE.repository;

import cloudweb.codefeedbackBE.dto.LoginUserDTO;
import cloudweb.codefeedbackBE.entity.User;

import java.util.HashMap;

public interface UserRepository {

    public Long save(User user);

    public void deleteUserByEmail(String email);

    public User userSignIn(HashMap<String, String> loginUser);

    Optional<User> findById(String userId);
}
