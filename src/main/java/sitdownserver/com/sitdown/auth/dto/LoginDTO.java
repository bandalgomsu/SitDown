package sitdownserver.com.sitdown.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sitdownserver.com.sitdown.auth.dto.SignUpDTO.TokenResponse;

public class LoginDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OauthLoginRequest {
        private String oauthAccessToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginResponse {
        private TokenResponse tokenResponse;
        private boolean needSignUp;
    }
}
