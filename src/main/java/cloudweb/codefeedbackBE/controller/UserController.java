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

    @PutMapping("/user/modify")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO, HttpServletRequest request, HttpSession session) {

        UserDTO2 loggedInUser = (UserDTO2) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(null, null, "로그인이 필요합니다."));
        }

        try {
            userService.updateUserByEmail(loggedInUser.getEmail(), updateUserDTO, session);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("회원정보 수정 성공", null, null));
        } catch (Exception e) {
            log.error("회원정보 수정 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @GetMapping("/session/check")
    public ResponseEntity<ResponseDTO> checkSession(HttpServletRequest request) {

        try {

            UserDTO2 loggedInUser = (UserDTO2) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(null, null, "세션이 유효하지 않습니다."));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("세션이 유효합니다.", loggedInUser, null));

        } catch (Exception e) {
            log.error("세션 체크 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }
}

