package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 한테이블에 모두 속성을 넣는다. +@Inheritance 는 DB의 슈퍼타입, 서브타입 관계를 매핑
@DiscriminatorColumn(name="dtype")  // 부모 클래스에 선언한다. 하위 클래스를 구분하는 용도의 컬럼
@Getter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;           // 이름
    private int price;            // 가격
    private int stockQuantity;   // 재고 수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories= new ArrayList<>();

    /* 비즈니스 로직의 추가 */
    // Item.addStock 하면 재고수량이 늘고, Item.removeStock하면 재고수량이 줄도록 설계
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }

}
