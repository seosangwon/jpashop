package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional // public 메서드들은 기본적으로 다 됨 , spring이 지원해주는 transactional 쓰는게 좋음
public class MemberService {
 //   @Autowired //filed 의존성 주입 (많이들 쓰지만) -> 요즘은 생성자로 많이 한다.
 //   private MemberRepository memberRepository;
    private final MemberRepository memberRepository;
    //final을 하면 좋은게 값을 변경 할수도 없고, 값이 처음에 까먹고 지정을 안해도 빨간 줄이 떠서 오류를 알려줌

    /**
     * 회원가입
     */
    @Transactional // 클래스 위에 어노테이션 해놓으면 public 메서드 들은 다 적용됨, 그렇지만 그냥 해봄
                    // 이것은 읽기 말고도 DB에 쓸 때
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); //
    }

    private void validateDuplicateMember(Member member){
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional(readOnly = true) // 조회에 읽기전용만하면 성능이 좋아짐
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    @Transactional(readOnly = true) // 조회에 읽기전용만하면 성능이 좋아짐
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);

    }


}
