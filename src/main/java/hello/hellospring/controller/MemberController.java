package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberservice;

    @Autowired
    public MemberController(MemberService memberservice) {
        this.memberservice = memberservice;
    }
}
