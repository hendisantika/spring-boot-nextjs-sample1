package id.my.hendisantika.backend.domain.member.service;

import id.my.hendisantika.backend.domain.member.entity.Member;
import id.my.hendisantika.backend.domain.member.repository.MemberRepository;
import id.my.hendisantika.backend.global.RsData.RsData;
import id.my.hendisantika.backend.global.RsData.config.SecurityUser;
import id.my.hendisantika.backend.global.RsData.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 31/12/24
 * Time: 06.28
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        String refreshToken = jwtProvider.genRefreshToken(member);
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);
        return member;
    }

    public boolean validateToken(String token) {
        return jwtProvider.verify(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh token does not exist."));
        String accessToken = jwtProvider.genAccessToken(member);

        return RsData.of("200-1", "Token renewal successful", accessToken);
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new SecurityUser(id, username, "", authorities);
    }

    @AllArgsConstructor
    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private Member member;
        private String accessToken;
        private String refreshToken;
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(String username, String password) {
        Optional<Member> optionalMember = this.memberRepository.findByUsername(username);

        // Same message for unknown user and wrong password, so usernames cannot be enumerated.
        if (optionalMember.isEmpty() || !passwordEncoder.matches(password, optionalMember.get().getPassword())) {
            return RsData.of("401-1", "Invalid username or password.");
        }

        Member member = optionalMember.get();

        // Generate AccessToken
        String accessToken = jwtProvider.genAccessToken(member);
        // Create RefreshToken
        String refreshToken = jwtProvider.genRefreshToken(member);

        return RsData.of("200-1", "Login successful", new AuthAndMakeTokensResponseBody(member, accessToken, refreshToken));
    }
}
