package id.my.hendisantika.backend.domain.member.service;

import id.my.hendisantika.backend.domain.member.entity.Member;
import id.my.hendisantika.backend.domain.member.repository.MemberRepository;
import id.my.hendisantika.backend.global.RsData.RsData;
import id.my.hendisantika.backend.global.RsData.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 05/11/26
 * Time: 06.38
 */
class MemberServiceTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private MemberRepository memberRepository;
    private JwtProvider jwtProvider;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        jwtProvider = mock(JwtProvider.class);
        memberService = new MemberService(memberRepository, jwtProvider, passwordEncoder);

        Member user1 = Member.builder()
                .username("user1")
                .password(passwordEncoder.encode("1234"))
                .build();
        when(memberRepository.findByUsername("user1")).thenReturn(Optional.of(user1));
        when(memberRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        when(jwtProvider.genAccessToken(any())).thenReturn("access");
        when(jwtProvider.genRefreshToken(any())).thenReturn("refresh");
    }

    @Test
    void loginSucceedsWithCorrectPassword() {
        RsData<MemberService.AuthAndMakeTokensResponseBody> rs = memberService.authAndMakeTokens("user1", "1234");

        assertThat(rs.getResultCode()).isEqualTo("200-1");
        assertThat(rs.getData().getAccessToken()).isEqualTo("access");
        assertThat(rs.getData().getMember().getUsername()).isEqualTo("user1");
    }

    @Test
    void loginFailsWithWrongPassword() {
        RsData<MemberService.AuthAndMakeTokensResponseBody> rs = memberService.authAndMakeTokens("user1", "wrong");

        assertThat(rs.getResultCode()).isEqualTo("401-1");
        assertThat(rs.getData()).isNull();
        verify(jwtProvider, never()).genAccessToken(any());
    }

    @Test
    void loginFailsWithUnknownUserUsingSameMessage() {
        RsData<MemberService.AuthAndMakeTokensResponseBody> unknown = memberService.authAndMakeTokens("ghost", "1234");
        RsData<MemberService.AuthAndMakeTokensResponseBody> wrongPassword = memberService.authAndMakeTokens("user1", "wrong");

        assertThat(unknown.getResultCode()).isEqualTo("401-1");
        assertThat(unknown.getData()).isNull();
        assertThat(unknown.getMsg()).isEqualTo(wrongPassword.getMsg());
    }
}
