package sitdownserver.com.sitdown.auth.jwt.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sitdownserver.com.sitdown.common.error.user.UserAccessDeniedException;
import sitdownserver.com.sitdown.user.entity.Role;
import sitdownserver.com.sitdown.user.entity.User;
import sitdownserver.com.sitdown.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));
//        return new PrincipalDetails(user);

        if (user.getRole() != Role.ADMIN) {
            throw new UserAccessDeniedException();
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().getKey())
                .build();
    }
}
