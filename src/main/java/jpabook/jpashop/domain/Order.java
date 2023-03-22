package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Delivery;
import jpabook.jpashop.domain.item.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name="orders")
@Entity
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Entity에 setter가 없는 이유가, 다른 곳에서 갑자기
// order 객체를 생성하고 수정하는 것을 막을려고한다. 위 롬복 코드를 해주면,  protected접근이기 때문에
// 다른 곳 에서 new Order 객체를 생성 할 때 빨간 줄 오류가 뜰 것이다.
public class Order {

    @Id@GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER,CANCEL]


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 메서드 // 주문 생성 완결
    public static Order createOrder(Member member,Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }





    //비즈니스 로직 //
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus()== DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }

    }

    //조회 로직//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice =0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();

        }
        return totalPrice;

    }


}
