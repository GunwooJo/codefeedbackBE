package cloudweb.codefeedbackBE.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private String title;
    private String content;
    private boolean isPublic;
}
