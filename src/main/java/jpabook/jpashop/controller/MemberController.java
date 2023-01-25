package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.request.MemberCreationRequest;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity readMembers(){
        return ResponseEntity.ok(memberService.findMembers());
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Member> readMember(@PathVariable Long id){
        return ResponseEntity.ok(memberService.findOne(id));
    }

    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@RequestBody MemberCreationRequest request){
        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(request.getAddress());
        return ResponseEntity.ok(memberService.join(member));
    }
}
