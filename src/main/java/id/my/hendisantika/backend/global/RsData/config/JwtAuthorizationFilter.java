package id.my.hendisantika.backend.global.RsData.config;

import id.my.hendisantika.backend.domain.member.service.MemberService;
import id.my.hendisantika.backend.global.RsData.RsData;
import id.my.hendisantika.backend.global.RsData.rq.Rq;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 31/12/24
 * Time: 06.10
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final Rq rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (!request.getRequestURI().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().equals("/api/v1/members/login") || request.getRequestURI().equals("/api/v1/members/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = rq.getCookie("accessToken");
        // Verify accessToken or issue refreshToken
        if (!accessToken.isBlank()) {
            // Token validity verification
            if (!memberService.validateToken(accessToken)) {
                String refreshToken = rq.getCookie("refreshToken");

                RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                rq.setCrossDomainCookie("accessToken", rs.getData());
            }
            // Get securityUser
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
            // Login Processing
            rq.setLogin(securityUser);
        }

        filterChain.doFilter(request, response);
    }
}
