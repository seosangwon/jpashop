package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // junit 시작할 때 스프링이랑 같이 시작 , 버전 업 되면 필요 없을수도
@SpringBootTest // 이 둘이 있어야 스프링과 완전 연동
@Transactional // 기본적으로  RollBack 해버려서 jpa 입장에서는 DB한테 insert 할 필요가 없는것임

class MemberServiceTest {
    @Autowired MemberService memberService; //테스트 케이스니까 간단하게 의존성주입
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false) // 이렇게 하면 RollBack 안함, DB에서 확인가능
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertEquals(member,memberRepository.findOne(saveId));

     }

   //  @Test(expected = IllegalStateException.class) // 강의에서는 문법오류 안났음
    @Test
     public void 중복_회원예약() throws Exception{
         //given
         Member member = new Member();
         member.setName("kim");


         Member member2 = new Member();
         member.setName("kim");

         //when
         memberService.join(member);
         memberService.join(member2); // 같은 이름이라 예외가 터져서 밖으로 나가야함, 더이상 메서드 진행 금지


         //then
         fail("예외가 발생해야 한다."); // 코드가 일로 오면 안되는데 와서 fail인거임


      }


}