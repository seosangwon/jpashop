package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링빈으로 등록
@RequiredArgsConstructor
public class MemberRepository  {

    /**
    @PersistenceContext //jpa 엔티티 매니저 , 스프링이 매니저 자동 주입
    private EntityManager em;
    이렇게 해도 되는데 이거 역시 스프링컨테이너에 빈 등록하고, 의존성 주입 받는것이다.
    => 이를 롬복으로 편하게 바꿀 수 있다.
**/
    private final EntityManager em; //생성자로 의존성 주입


    public void save(Member member){
        em.persist(member);
    } // 영속성 컨텍스트에 객체를 올리는 순간 key(pk)와 value 값을 받음

    public Member findOne (Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){ // jpql 쿼리문 : From의 대상이 테이블이 아닌 Entity이다.
       return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
