package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)   // 주문 1개당, member 1명, member 1명당, 주문여러개 , 즉시로딩 x, 지연로딩하도록 해야함.
    @JoinColumn(name="member_id")  // 조인을 member_id로 한다.
    private Member member;

    // 주문 상품 1개당 주문 하나에 종속된다. + OrderItem클래스의 필드인 order에 매핑된다.
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL) // cascade 를 통해서 컬렉션안의 값들을 일일이 persist안해도 자동으로 전파해준다.
    private List<OrderItem> orderItems=new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL) // cascade를 통해서 delivery 값들을 모두 자동 전파
    @JoinColumn(name="delivery_id")  // 상속관계의 주인 : Order로 설정했다.
    private Delivery delivery;

    private LocalDateTime orderDate;     // 주문시간

    @Enumerated(EnumType.STRING)     // Enum타입에 무조건 붙여주기 - 디폴트는 ORIGINAL로 숫자이다.
    private OrderStatus status;    // 주문 상태 [ORDER : 주문완료 , CANCEL: 주문취소]


    /* 연관 관계 메서드 - 한 코드로 양쪽 세팅 완료 */
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    /* 생성 메서드 */  // 주문생성하는부분 (직접 주문을 누르면~ )  생성에 변경이 생기면 이부분만 수정하면됨.

    public static Order createOrder(Member member,Delivery delivery,OrderItem... orderItems){
        Order order=new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 처음상태를 주문완료 상태로 강제로 세팅
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    protected Order (){
        // Order를 임의로 생성해서 안에 값을 조작하는 것을 방지한다. 유지보수가 어려워지기 때문에
    }

    // 비즈니스 로직 //
    /**
     * 주문취소
     */
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);  // 주문상태를 취소상태로설정
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();   // 주문취소시 수량 원복
        }
    }

    // 조회 로직 //

    /**
     * 전체 주문가격 조회
     */
    public int getTotalPrice(){
        int totalPrice=0;
        for(OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}

