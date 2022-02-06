package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY) // FK를 두 클래스중 어디에 선언해도 무관 but 많이 찾는곳 선언 Order에 선언, delivery 인스턴스 이름으로 매핑됨
    private Order order;             // 특정주문 1개당 1개의 특정배송을 갖는다,


    @Embedded
    private Address address;


    @Enumerated(EnumType.STRING)  // 디폴트가 ORIGINAL이라 숫자로 들어가기 때문에 STRING 지정해준다.
    private DeliveryStatus status ; // READY: 배송준비 , COMP : 배송완료

}
