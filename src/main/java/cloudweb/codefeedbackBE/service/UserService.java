package cloudweb.codefeedbackBE.service;

import cloudweb.codefeedbackBE.dto.UpdateUserDTO;
import cloudweb.codefeedbackBE.dto.UserDTO;
import cloudweb.codefeedbackBE.dto.UserDTO2;
import cloudweb.codefeedbackBE.entity.User;
import cloudweb.codefeedbackBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void userSignUp(UserDTO userDTO) {

        String encodedPw = passwordEncoder.encode(userDTO.getPassword());

        User user = User.builder()
                .email(userDTO.getEmail())
                .password(encodedPw)
                .nickname(userDTO.getNickname())
                .build();

        userRepository.save(user);
    }

    public void deleteUserByEmailAndPassword(String email, String password) {

        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }


    @Transactional(readOnly = true)
    public Optional<UserDTO2> userSignIn(HashMap<String, String> loginUser) {

        String plainPw = loginUser.get("password");
        Optional<User> foundUser = userRepository.findByEmail(loginUser.get("email"));

        if (foundUser.isPresent() && passwordEncoder.matches(plainPw, foundUser.get().getPassword())) {

            UserDTO2 user = UserDTO2.builder()
                    .email(foundUser.get().getEmail())
                    .nickname(foundUser.get().getNickname())
                    .build();

            return Optional.of(user);
        }

        return Optional.empty();
    }

    public Optional<UserDTO2> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> UserDTO2.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build());
    }

    public User updateUserByEmail(String email, UpdateUserDTO updateUserDTO) {
        return userRepository.findByEmail(email).map(user -> {
            user.setNickname(updateUserDTO.getNickname());
            user.setPassword(updateUserDTO.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}

