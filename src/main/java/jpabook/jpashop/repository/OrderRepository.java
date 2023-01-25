package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    /**
     * QueryDSL로 동적 쿼리 생성 -> 강의 후반부에 다시 배울 예정
     * */
    public List<Order> findAll(OrderSearch orderSearch){
        // 주문상태, 주문자 이름이 없는 경우에는 상관 없이 가져올 수 있도록 동적 쿼리로 짜야 한다.
        // 나중에 배울 예정.. 일단 Criteria 이용한 방법 코드 가져오기

//        동적 쿼리 X
        return em.createQuery("select o from Order o join o.member m" +
                        " where o.orderStatus = :status" +
                        " and m.name = :name",
                        Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)    // 최대 100건(페이징)
                .getResultList();
    }

}
