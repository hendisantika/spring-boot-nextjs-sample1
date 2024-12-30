package id.my.hendisantika.backend.domain.member.service;

import id.my.hendisantika.backend.domain.member.repository.MemberRepository;
import id.my.hendisantika.backend.global.RsData.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
