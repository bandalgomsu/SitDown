package sitdownserver.com.sitdown.auth.jwt.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sitdownserver.com.sitdown.auth.dto.LoginDTO.LoginResponse;
import sitdownserver.com.sitdown.auth.dto.SignUpDTO.OauthSignUpRequest;
import sitdownserver.com.sitdown.auth.dto.SignUpDTO.TokenResponse;
import sitdownserver.com.sitdown.auth.entity.OAuthAttributes;
import sitdownserver.com.sitdown.common.error.user.UserNotFoundException;
import sitdownserver.com.sitdown.external.OauthApi;
import sitdownserver.com.sitdown.user.entity.Role;
import sitdownserver.com.sitdown.user.entity.SocialType;
import sitdownserver.com.sitdown.user.entity.User;
import sitdownserver.com.sitdown.user.repository.UserRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OauthApi oauthApi;

    public LoginResponse loginOauth(String oauthAccessToken, SocialType socialType) {
        OAuthAttributes attributes = OAuthAttributes.of(socialType,
                oauthApi.getOauthUserInfo(oauthAccessToken, socialType));

        User user = getUser(attributes, socialType);

        String accessToken = jwtService.createAccessToken(user.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(user.getEmail(), refreshToken);

        TokenResponse tokenResponse = TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
                .build();

        if (user.getRole().equals(Role.GUEST)) {
            return LoginResponse.builder()
                    .tokenResponse(tokenResponse)
                    .needSignUp(true)
                    .build();
        }

        return LoginResponse.builder()
                .tokenResponse(tokenResponse)
                .build();
    }

    private User getUser(OAuthAttributes attributes, SocialType socialType) {
        User findUser = userRepository.findBySocialTypeAndSocialId(socialType, attributes.getOauth2UserInfo().getId())
                .orElse(null);

        if (findUser == null) {
            return saveUser(attributes, socialType);
        }

        return findUser;
    }

    private User saveUser(OAuthAttributes attributes, SocialType socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }

    public void signUpOauth(OauthSignUpRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userRepository.save(user);
    }


    public TokenResponse reissuedRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(
                UserNotFoundException::new);
        String reissuedRefreshToken = jwtService.createRefreshToken();
        String accessToken = jwtService.createAccessToken(user.getEmail());

        user.setRefreshToken(reissuedRefreshToken);

        userRepository.save(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(reissuedRefreshToken)
                .build();
    }
}
