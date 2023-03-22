package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Delivery;
import jpabook.jpashop.domain.item.DeliveryStatus;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    /**
     * 주문
     */
    @Transactional // DB변경
    public Long order(Long memberId, Long itemId , int count){
        //엔티티 조회 , 회원의Id와 , 상품의 Id값을 넘겨받아야 함
        Member member = memberRepository.findOne(memberId); //멤버 찾기
        Item item = itemRepository.findOne(itemId); //item 찾기

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); // cascade 덕분에 ,oder item, delivery 자동으로 DB에 persist 된다.
                                     // order가 delivery와 orderitem을 관리하기에 같이 cascade로 묶인다.
                                     // 다 cascade로 묶여있기에, 다른 곳에서도 deliveery와 odertiem을 사용한다면 cascade 하면 안된다.
                                     // 보통은 안쓰는 것이좋고, 나중에 묶이는걸 알았을 때 cascade로 리팩토링하는게 좋음
        return order.getId();

    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){

        Order order = orderRepository.findOne(orderId); //order Entity 조회
        order.cancel(); // Order 취소 : 비즈니스 로직 생성되어있어서 이렇게 사용이 가능 :jpa의 장점
                        // mybatis, jdbc 템플릿이면 직접 Query문 다 짜서 저장하고, 취소하고 해야 함
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }


}
