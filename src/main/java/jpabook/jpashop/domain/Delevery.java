package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delevery {
    @Id @GeneratedValue
    @Column(name = "delevery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order oder;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeleveryStatus deleveryStatus;
}
