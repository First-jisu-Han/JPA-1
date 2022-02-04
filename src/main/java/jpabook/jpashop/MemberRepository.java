package jpabook.jpashop;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext   // 이 어노테이션이 있으면 스프링 부트가 EntityManager를 자동주입해줌
    private EntityManager em;


    // 저장하는 코드
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class,id);
    }
}
