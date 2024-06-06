package cloudweb.codefeedbackBE.controller;

import cloudweb.codefeedbackBE.dto.LoginUserDTO;
import cloudweb.codefeedbackBE.dto.PostDTO;
import cloudweb.codefeedbackBE.dto.PostModifyDTO;
import cloudweb.codefeedbackBE.dto.ResponseDTO;
import cloudweb.codefeedbackBE.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<ResponseDTO> savePost(@RequestBody @Valid PostDTO postDTO, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginUserDTO loggedInUser = (LoginUserDTO)session.getAttribute("loggedInUser");
        try {
            postService.savePost(postDTO, loggedInUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Post 저장 성공", null, null));

        } catch (Exception e) {

            log.error("게시글 저장 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<ResponseDTO> modifyPost(@RequestBody @Valid PostModifyDTO postModifyDTO, @PathVariable Long id) {

        try {
            postService.modifyPost(id, postModifyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Post 수정 성공", null, null));

        } catch (Exception e) {

            log.error("post 수정 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(null, null, e.getMessage()));
        }
    }
}