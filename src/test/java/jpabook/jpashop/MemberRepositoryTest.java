//package jpabook.jpashop;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @Transactional  // test 끝나면 자동으로 데이터 roll-back -> test에서만 그렇게 작동
//    //@Rollback(false)    // roll-back 하지 않고 싶을 때 적용
//    public void testMember(){
//        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long save = memberRepository.save(member);  // option+command+v를통해 리턴값을 받으면 편함
//        Member member1 = memberRepository.find(save);
//
//        //then
//        Assertions.assertThat(member1.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(member1.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(member1).isEqualTo(member);   // 이렇게도 비교 가능 -> 값이 같은지 비교
//        System.out.println("member == member1 : " + (member == member1));
//        // transactional이 있기 때문에, 같은 영속성 context 안에서는 id값이 같으면 같은 것으로 본다.
//    }
//
//
//}