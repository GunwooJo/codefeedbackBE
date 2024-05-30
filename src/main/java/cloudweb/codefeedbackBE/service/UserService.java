package cloudweb.codefeedbackBE.service;

import cloudweb.codefeedbackBE.dto.UserDTO;
import cloudweb.codefeedbackBE.entity.User;
import cloudweb.codefeedbackBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
