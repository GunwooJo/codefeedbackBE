package cloudweb.codefeedbackBE.service;

import cloudweb.codefeedbackBE.dto.LoginUserDTO;
import cloudweb.codefeedbackBE.dto.UserDTO;
import cloudweb.codefeedbackBE.entity.User;
import cloudweb.codefeedbackBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long userSignUp(UserDTO userDTO) {

        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .build();

        return userRepository.save(user);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }

    public LoginUserDTO userSignIn(HashMap<String, String> loginUser) {
        User loginedUser = userRepository.userSignIn(loginUser);
        return LoginUserDTO.builder()
                .email(loginedUser.getEmail())
                .nickname(loginedUser.getNickname())
                .build();
    }
}
