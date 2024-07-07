package cloudweb.codefeedbackBE.controller;


import cloudweb.codefeedbackBE.dto.*;
import cloudweb.codefeedbackBE.entity.User;
import cloudweb.codefeedbackBE.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user/join")
    public ResponseEntity<ResponseDTO> signUp(@RequestBody @Valid UserDTO userDTO) {

        try {
            userService.userSignUp(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("회원가입 성공", userDTO, null));

        } catch (Exception e) {

            log.error("유저 회원가입 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<ResponseDTO> delete(@RequestBody HashMap<String, String> request) {

        String email = request.get("email");
        String password = request.get("password");

        try {
            userService.deleteUserByEmailAndPassword(email, password);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("회원탈퇴 성공", null, null));
        } catch (Exception e) {
            log.error("회원탈퇴 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }


    @PostMapping("/user/login")
    public ResponseEntity<ResponseDTO> signIn(@RequestBody HashMap<String, String> loginUser, HttpSession session) {

        try {

            Optional<UserDTO2> optionalUser = userService.userSignIn(loginUser, session);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(null, null, "아이디나 비밀번호가 틀리거나 존재하지 않는 회원입니다."));
            }

            UserDTO2 user = optionalUser.get();

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("로그인 성공", user, null));

        } catch (Exception e) {
            log.error("유저 로그인 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> updateUser(@RequestHeader("email") String email, @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        try {
            User updatedUser = userService.updateUserByEmail(email, updateUserDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("회원정보 수정 성공", updatedUser, null));
        } catch (Exception e) {
            log.error("회원정보 수정 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @PostMapping("/user/info")
    public ResponseEntity<ResponseDTO> getUserInfo(@RequestBody UserDTO3 userDTO3) {

        try {
            Optional<UserDTO2> userDTO = userService.findUserByEmail(userDTO3.getEmail());

            if (userDTO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(null, null, "사용자 정보를 찾을 수 없습니다."));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("사용자 정보 가져오기 성공", userDTO, null));

        } catch (Exception e) {
            log.error("사용자 정보 가져오기 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }
}

