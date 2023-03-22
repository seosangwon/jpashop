package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

@Getter @Setter
public class MemberForm {

   @NotBlank(message="회원이름은 필수 입니다.") //최신 스프링부트버젼에는 import가 안됨
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
