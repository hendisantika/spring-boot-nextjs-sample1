package id.my.hendisantika.backend.domain.member.controller;

import id.my.hendisantika.backend.domain.member.service.MemberService;
import id.my.hendisantika.backend.global.RsData.rq.Rq;
import lombok.RequiredArgsConstructor;
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
}
