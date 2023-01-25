package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Item save(Item item){
        if(item.getId() == null){   // 완전히 새로 생성한 객체인 경우, 신규 등록
            em.persist(item);
            return item;
        }
        else {  // 디비에 등록한 객체를 가져와서 update한다고 생각하면 됨.
            em.merge(item);
            return item;
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
