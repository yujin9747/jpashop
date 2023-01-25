package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)    // Order만 호출해 주면, delivery까지 같이 persiste되도록.

    @JoinColumn(name = "delivery_id")
    private Delevery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태 [ORDER , CANCEL]

    // 양방향인 경우 setter에서 아래와 같이 해주면 코드 라인을 줄일 수 있다.
    //==연관관계 편의 메서드==// --> 연관관계의 주인 Entity가 가지고 있는 것이 좋다.
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delevery delivery){
        this.delivery = delivery;
        delivery.setOder(this);
    }

    //==생성 메서드==// -> 생성이 복잡한 경우 생성 메서드를 따로 만들어 주는 것이 좋다. 최대한 도메인 주도 개발을 하는 것이 자바에 적합함.
    // 연관관계를 모두 매핑하면서 생성될 수 있도록 작성할 것!
    public static Order createOrder(Member member, Delevery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     * */
    public void cancel(){
        if(delivery.getDeleveryStatus() == DeleveryStatus.COMP){    // 이미 배송 완료
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setOrderStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: this.orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체주문가격 조회
     * */
    public int getTotalPrice(){
        int total=0;
        for(OrderItem orderItem : orderItems){
            total += orderItem.getTotalPrice(); // 최대한 도메인 주도 개발하기 위해 계산 메서드를 따로 만든다
        }
        return total;
    }
}
