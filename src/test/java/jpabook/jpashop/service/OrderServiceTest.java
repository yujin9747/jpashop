package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("jpa study", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER,  getOrder.getOrderStatus()); // 주문상태가 ORDER인지 확인
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문가격은 가격*수량이다", 10000*orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다", 8, book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("jpa study", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("재고가 정상적으로 복구가 되어야 한다.", 10, book.getStockQuantity());
        assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getOrderStatus());
    }

    @Test(expected = NotEnoughException.class)
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("jpa study", 10000, 10);
        int orderCount = 11;

        // when
        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        fail("재고수량 예외가 발생해야 한다.");
    }

    private Book createBook(String name, int price, int count) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(count);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("yujin");
        member.setAddress(new Address("seoul", "river", "123-123"));
        em.persist(member);
        return member;
    }
}