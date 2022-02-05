package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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

    @ManyToOne   // 주문 1개당, member 1명, member 1명당, 주문여러개
    @JoinColumn(name="member_id")  // 조인을 member_id로 한다.
    private Member member;


    @OneToMany(mappedBy = "order")  // 주문 상품 1개당 주문 하나에 종속된다. + OrderItem클래스의 필드인 order에 매핑된다.
    private List<OrderItem> orderItems=new ArrayList<>();


    @OneToOne
    @JoinColumn(name="delivery_id")  // 상속관계의 주인 : Order로 설정했다.
    private Delivery delivery;

    private LocalDateTime orderDate;       // 주문시간

    @Enumerated(EnumType.STRING)     // Enum타입에 무조건 붙여주기 - 디폴트는 ORIGINAL로 숫자이다.
    private OrderStatus status;    // 주문 상태 [ORDER : 주문완료 , CANCEL: 주문취소]
}
