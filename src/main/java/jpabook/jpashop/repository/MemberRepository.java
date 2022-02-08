package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // 스프링이 자동으로 엔티티메니저를 만들어서 여기에 자동 주입


    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class,id); // Member.class 에서 id를 찾아서 해당 멤버를 반환. (타입,PK)
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();   // JPQL 의 사용 ( 테이블을 대상으로가 아니라 엔티티를 대상으로 함 )
    }


    // 이름에 의한 회원 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name= :name",Member.class)
                .setParameter("name",name)  // 위의 JPQL에서 name을 바인딩 해준다.
                .getResultList();  // JPQL 결과 리스트를 뽑아서 전달
    }

}
