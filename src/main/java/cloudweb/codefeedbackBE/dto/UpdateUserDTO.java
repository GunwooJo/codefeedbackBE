package cloudweb.codefeedbackBE.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private String nickname;
    private String password;
}
