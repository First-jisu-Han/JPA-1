package jpabook.jpashop.service;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    // 의존관계주입은 생성자를 통해이뤄짐 final붙은것들 자동으로 생성자만들어서 파라미터로 넣러줌 + 롬복으로 생성자 생성
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId,Long itemId,int count){
        /* 엔티티 조회 */
        Member member = memberRepository.findOne(memberId);
        Item item=itemRepository.findOne(itemId);
        /* 배송 정보 생성 */
        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());
        /* 주문 상품 생성 */
        OrderItem orderItem=OrderItem.createOrderItem(item,item.getPrice(),count);
//        OrderItem orderItem=new OrderItem();
//        orderItem.setCount(count); 등등으로 하면 생성로직 변경이 있으면, 유지보수에 상당히 불편하다.
//        따라서 OrderItem 클래스는 생성자를 protected로 하도록한다. (이미 OrderItem의 createOrderItem에서 생성하는 메서드를 만들었기때문)
        /* 주문 생성 */
        Order order=Order.createOrder(member,delivery,orderItem);
        /* 주문 저장 */
        orderRepository.save(order); // 주문만 저장해줘도 cascade 조건때문에 다른 요소들 자동 persist한다.
        return order.getId();
    }
    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        Order order=orderRepository.findOne(orderId);
        order.cancel(); // 재고 다시돌려놓고, 상태 취소상태로 - Order.class 참조
    } // JPA가 자동으로 변경내용을 토대로 업데이트 쿼리를 날린다.

    /* 주문검색 */
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll();
//    }

}
