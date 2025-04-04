package id.my.hendisantika.backend.domain.member.controller;

import id.my.hendisantika.backend.domain.member.dto.MemberDto;
import id.my.hendisantika.backend.domain.member.entity.Member;
import id.my.hendisantika.backend.domain.member.service.MemberService;
import id.my.hendisantika.backend.global.RsData.RsData;
import id.my.hendisantika.backend.global.RsData.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 31/12/24
 * Time: 06.34
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1memberController {
    private final MemberService memberService;
    private final Rq rq;

    @Getter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginResponseBody {
        private MemberDto memberDto;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody loginRequestBody) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.authAndMakeTokens(loginRequestBody.getUsername(), loginRequestBody.getPassword());

        // Insert accessToken and refreshToken tokens into cookies
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().getAccessToken());
        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().getRefreshToken());

        return RsData.of(authAndMakeTokensRs.getResultCode(), authAndMakeTokensRs.getMsg(), new LoginResponseBody(new MemberDto(authAndMakeTokensRs.getData().getMember())));
    }

    public record MeResponseBody(MemberDto member) {
    }

    @GetMapping("/me")
    public RsData<MeResponseBody> me() {
        Member member = rq.getMember();

        return RsData.of(
                "200",
                "My information search was successful",
                new MeResponseBody(new MemberDto(member))
        );
    }

    @PostMapping("/logout")
    public RsData<Void> logout() {
        rq.removeCrossDomainCookie("accessToken");
        rq.removeCrossDomainCookie("refreshToken");

        return RsData.of("200", "Logout successful");
    }
}
