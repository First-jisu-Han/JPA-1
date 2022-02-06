package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;


    @ManyToMany
    @JoinTable(name="category_item", // 조인되는 테이블 이름
            joinColumns = @JoinColumn(name="category_id"), // 중간에 들어가는 카테고리의 id
            inverseJoinColumns = @JoinColumn(name="item_id"))  // 아이템쪽 컬럼
    private List<Item> items=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="parent_id")  // parent_id로 조인
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child =new ArrayList<>();

}
