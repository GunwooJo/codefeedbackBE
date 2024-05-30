package cloudweb.codefeedbackBE.controller;


import cloudweb.codefeedbackBE.dto.ResponseDTO;
import cloudweb.codefeedbackBE.dto.UserDTO;
import cloudweb.codefeedbackBE.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
