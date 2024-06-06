package cloudweb.codefeedbackBE.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private String title;
    private String content;
    private boolean isPublic;
    private List<MessageDTO> messages = new ArrayList<>();
}
