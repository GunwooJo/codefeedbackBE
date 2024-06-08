package cloudweb.codefeedbackBE.service;

import cloudweb.codefeedbackBE.dto.MessageDTO;
import cloudweb.codefeedbackBE.dto.PostDTO;
import cloudweb.codefeedbackBE.dto.PostModifyDTO;
import cloudweb.codefeedbackBE.entity.Message;
import cloudweb.codefeedbackBE.entity.Post;
import cloudweb.codefeedbackBE.entity.User;
import cloudweb.codefeedbackBE.repository.PostRepository;
import cloudweb.codefeedbackBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void savePost(PostDTO postDTO, String userEmail) {

        User foundUser = userRepository.findByEmail(userEmail);
        Post post = new Post();
        post.setUser(foundUser);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAccess(postDTO.isAccess());

        for (MessageDTO messageDTO : postDTO.getMessages()) {
            Message message = new Message();
            message.setRole(messageDTO.getRole());
            message.setCreatedAt(messageDTO.getCreatedAt());
            message.setContent(messageDTO.getContent());

            post.addMessage(message);
        }

        postRepository.save(post);
    }

    public void modifyPost(Long postId, PostModifyDTO postModifyDTO) {

        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("아이디가 " + postId + "인 post 찾을 수 없음."));

        foundPost.setTitle(postModifyDTO.getTitle());
        foundPost.setContent(postModifyDTO.getContent());
        foundPost.setAccess(postModifyDTO.isAccess());
    }

    public void deletePost(Long postId){
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("아이디가 " + postId + "인 post 찾을 수 없음."));
    }
}
