package cloudweb.codefeedbackBE.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginUserDTO {
    @NotBlank
    @Email
    private String email;

    @NotEmpty
    private String nickname;
}
