package jpabook.jpashop;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext       // 이 어노테이션이 있으면 스프링 부트가 EntityManager를 자동주입해줌
    private EntityManager em; //영속성 컨텍스트의 특징:엔티티 매니저를 생성할 때 하나 만들어진다.
                              // 엔티티 매니저를 통해서 영속성 컨텍스트에 접근하고 관리할 수 있다.


    // 저장하는 코드
    public Long save(Member member){
        em.persist(member);  // 영속성 컨텍스트: 엔티티를 영구 저장하는 환경이라는 뜻이다.
        // 애플리케이션과 데이터베이스 사이에서 객체를 보관하는 가상의 데이터베이스 같은 역할
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class,id); //  em.find(엔티티 클래스 타입, 식별자 값);
    }
}
