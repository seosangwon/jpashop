package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Member {

    @Id@GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 하나의 회원이 여러개의 상품 주문 가능하기 때문에 일대 다 관계
    // mappedBy는 내가 맵핑하지 member한테 맵핑 당하는 쪽이다. 연관관계의 주인이 아닌 거울일 뿐이다.
    private List<Order> orders = new ArrayList<>();




}
