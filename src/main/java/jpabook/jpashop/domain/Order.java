package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
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
    //==연관관계 편의 메서==// --> 연관관계의 주인 Entity가 가지고 있는 것이 좋다.
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
}
