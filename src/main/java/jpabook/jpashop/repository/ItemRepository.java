package jpabook.jpashop.repository;


import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor   // 롬복 라이브러리 - 생성자 자동 생성 (final붙은것 매개변수로해서 자동주입)
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
       if(item.getId()==null){   // 만약 id를 갖고있지 않다면 , persist 수행 ( 새로등록하는 )
           em.persist(item);
       }
       em.merge(item);          // 만약 id를 갖고 있다면, merge 수행  ( update 하는 느낌 )
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);  // Item.class 에서 id 를 찾아서 반환
    }


    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

}
