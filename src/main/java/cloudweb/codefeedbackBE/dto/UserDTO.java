package cloudweb.codefeedbackBE.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserDTO {

    private String email;
    private String password;
    private String nickname;
}
