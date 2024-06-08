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

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
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
    public ResponseEntity<ResponseDTO> delete(@RequestParam String email) {

        try {
            userService.deleteUserByEmail(email);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("회원탈퇴 성공", null, null));

        } catch (Exception e) {

            log.error("회원탈퇴 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<ResponseDTO> signIn(@RequestBody HashMap<String, String> loginUser, HttpSession session) {

        try {
            UserDTO2 user = userService.userSignIn(loginUser);
            session.setAttribute("loggedInUser", user);
            session.setMaxInactiveInterval(1800);   //세션 유효시간 30분 = 1800초

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("로그인 성공", user, null));

        } catch (Exception e) {
            log.error("유저 로그인 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        try {
            User updatedUser = userService.updateUser(userId, updateUserDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("회원정보 수정 성공", updatedUser, null));
        } catch (Exception e) {
            log.error("회원정보 수정 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @GetMapping("/user/info")
    public ResponseEntity<ResponseDTO> getUserInfo(HttpServletRequest request) {
        try {
            UserDTO2 loggedInUser = (UserDTO2) request.getSession(false).getAttribute("loggedInUser");

            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(null, null, "로그인이 필요합니다."));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("사용자 정보 가져오기 성공", loggedInUser, null));

        } catch (Exception e) {
            log.error("사용자 정보 가져오기 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }
}
