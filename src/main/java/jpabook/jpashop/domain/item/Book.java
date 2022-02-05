package jpabook.jpashop.domain.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")  // 엔티티 저장시, 구분 컬럼에 입력할 값을 지정. 엔티티를 저장할때 부모클래스 dtype에 B가 저장된다.
@Getter
@Setter
public class Book extends Item {

    private String author;
    private String isbn;


}
