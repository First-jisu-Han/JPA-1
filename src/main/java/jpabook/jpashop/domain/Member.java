package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")   // id필드의 값 말고, 컬럼 이름으로 member_id라고 부여한다.
    private Long id;             // 회원 ID

    private String name;         // 회원 이름

    @Embedded   // 내장 타입을 포함했다는 의미
    private Address address;     // 회원 주소

    // 주문 1개당, member 1명, member 1명당, 주문여러개
    @OneToMany(mappedBy = "member")  // +mapped by 사용을 통해서 member라는 필드로 매핑만된것임을 알림 ( 연관관계의 주인이 아님을 알림, 읽기 전용 )
    private List<Order> orders = new ArrayList<>();  // 회원 주문 정보

}
