package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Delivery {

    @Id@GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // Orinal로 하면 숫자 0,1,2 중간에 다른거 생기면 바로 망하는거 쓰지마셈
    private DeliveryStatus status; //READY, COMP

}
