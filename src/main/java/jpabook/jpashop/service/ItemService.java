package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;  // 롬복을통해서 생성자가 생성되는 즉시 이 필드가 주입되도록 설정완료


    @Transactional   // 기본이 readOnly 이기 떄문에 데이터 추가는 readOnly말고 다른것을 해준다.
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    // 변경 감지기능
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem=itemRepository.findOne(itemId);    // 영속 상태이다. (JPA가 관리) + setter로 설계하기보단 추가적으로 메서드로 만들어값을 넣도록 설계해야한다.
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
    } // 메서드 호출이후 변경사항에 대해서 UPDATE SQL을 날린다.

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
