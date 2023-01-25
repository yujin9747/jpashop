package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)    // 조회 서비스에서 readonly=true로 성능 최적화
@RequiredArgsConstructor    // final 필드 가지고 생성자를 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional  // 조회가 아닌 서비스에서는 readonly=false가 기본인 transactional 어노테이션을 붙여야 한다.
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> result = memberRepository.findByName(member.getName());
        if(!result.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
