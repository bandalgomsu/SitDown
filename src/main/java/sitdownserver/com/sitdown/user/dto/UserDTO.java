package sitdownserver.com.sitdown.user.dto;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sitdownserver.com.sitdown.user.entity.Role;
import sitdownserver.com.sitdown.user.entity.SocialType;
import sitdownserver.com.sitdown.user.entity.User;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class UserInfoResponse {
        private Long id;
        private String email;
        private String password;
        private String nickname;
        private String profileImage;
        private Role role;
        private SocialType socialType;
        private String socialId;
        private String birth;
        private String gender;
        private String name;

        public static UserInfoResponse toDTO(User user) {
            return new UserInfoResponse(user.getId(), user.getEmail(), user.getPassword(), user.getNickname(),
                    user.getProfileImage(), user.getRole(), user.getSocialType(), user.getSocialId(), user.getBirth(),
                    user.getGender(), user.getName()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class UserInfoResponses {
        private List<UserInfoResponse> responses;

        public static UserInfoResponses toDTO(List<User> users) {
            List<UserInfoResponse> responses = new ArrayList<>();

            users.forEach(user -> responses.add(UserInfoResponse.toDTO(user)));

            return new UserInfoResponses(responses);
        }
    }

}
