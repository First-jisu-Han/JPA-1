package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class
OrderItem {


    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;  // 주문 당시의 가격

    private int count;       // 주문 당시의 수량

    protected OrderItem(){
        // protected로 막아서 생성한 후에 값들을 일일이 대입하지않도록, 하나로 묶어서 Order에서 관리하도록 하기 위함이다.
    }

    // 생성메서드
    public static OrderItem createOrderItem(Item item,int orderPrice, int count){
        OrderItem orderItem=new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 재고가 줄어들도록함.
        return orderItem;
    }

    // 비즈니스 로직 - 주문취소시 , 주문 상품도 취소가 되어야됨.
    public void cancel() {
        getItem().addStock(count); // 주문수량만큼 다시 재고개수 추가 (원복)
    }

    // 조회로직 //
    /**
     * 주문상품 전체 가격조회
     */
    public int getTotalPrice() {
        return getOrderPrice()* getCount();
    }
}
