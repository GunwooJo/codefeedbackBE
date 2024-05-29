package cloudweb.codefeedbackBE.entity;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    private String email;
    private String password;
    private String nickname;

}
