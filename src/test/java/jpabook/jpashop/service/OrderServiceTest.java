package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member =createMember("회원A",new Address("서울","서울로","222-222"));
        /*  createMember 메서드로 간략화 했다.
Member member= new Member();
member.setName("회원A");
member.setAddress(new Address("서울","서울로","222-222"));
em.persist(member);
         */
        Book book =createBook("JPA책",2000,200);
        /*  createBook 메서드로 간략화 했다.
        Book book =new Book();
        book.setName("JPA책");
        book.setPrice(2000);
        book.setStockQuantity(200);  //재고 200개 설정
        em.persist(book);
        */
        int orderCount=2;  // 주문 수량
        // when
        Long orderId=orderService.order(member.getId(), book.getId(),2);
        // then
        Order getOrder=orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER,getOrder.getStatus());     // 주문한 상품이 주문저장소에 들어가서 주문상태가 맞는지
        Assertions.assertEquals(1,getOrder.getOrderItems().size()); //상품 종류 1가지 주문된게 맞는지
        Assertions.assertEquals(2000*orderCount,getOrder.getTotalPrice() ); // 주문 가격이 맞는지
        Assertions.assertEquals(198,book.getStockQuantity()); // 남은 수량
    }
    @Test(expected= NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member =createMember("회원A",new Address("서울","서울로","222-222"));
        Item item =createBook("JPA책",2000,200);
        int orderCount=201;
        // when
        Long orderId=orderService.order(member.getId(),item.getId(),orderCount);
        // then
        fail("재고수량 부족 예외가 발생할 수 있다. ");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member=createMember("회원A",new Address("경기","성남로", "111-111"));
        Item item=createBook("SQL책",2000,100);
        int orderQuantity=90;
        //when
        Long orderId= orderService.order(member.getId(),item.getId(),orderQuantity);
        orderService.cancelOrder(orderId);  // 주문 취소

        Order order=orderRepository.findOne(orderId);
        //then
        Assertions.assertEquals(OrderStatus.CANCEL,order.getStatus()); // 주문 취소상태여야한다.
        Assertions.assertEquals(item.getStockQuantity(),100);  // 다시 재고가 원복 되어야한다.

    }

    public Member createMember(String name,Address address){
        Member member= new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
    public Book createBook(String name,int price,int quantity){
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }


}