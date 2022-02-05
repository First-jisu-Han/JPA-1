package jpabook.jpashop;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {
    // @GeneratedValue : 기본 설정 값으로 각 데이터베이스에 따라 기본키를 자동으로 생성,
    // @Id 애노테이션은 JPA 엔티티 객체의 식별자로 사용할 필드에 적용
    @Id @GeneratedValue
    private Long id;
    private String username;

}
