package sitdownserver.com.sitdown.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sitdownserver.com.sitdown.user.entity.SocialType;
import sitdownserver.com.sitdown.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
