package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false) //-> insert문을 commit 해서 직접 눈으로 확인하기 위함. Transactional이 테스트 메서드에 있으면 기본적으로 rollback을 한다.
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Member getMember = memberService.join(member);

        // then
        /**em.flush(); 영속성 context 안에 있는 내용들을 쿼리를 보낸다.
         * 쿼리를 보고싶을 때 사용.
         * @RollBack(false)가 없으면 롤백은 그대로 진행.**/
        assertEquals(member, memberRepository.findOne(getMember.getId()));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        memberService.join(member2);

//        try{
//            memberService.join(member2);
//        }
//        catch (IllegalStateException e){
//            return;
//        }

        // then
        fail("예외가 발생해야 한다.");   // 정상작동할 경우 catch문에서 return이 되었어야 한다는 의미.
    }

}