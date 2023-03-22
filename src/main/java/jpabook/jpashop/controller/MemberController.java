package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){ //memberform이라는 이름으로
        model.addAttribute("memberForm",new MemberForm());// Controller에서 해당 view로 넘어갈 때 data를 실어서 넘김
        return "members/createMemberForm";
    }
    @PostMapping("/members/new") // 실제 data를 DB에 등록
    public String create(@Validated MemberForm form, BindingResult result){ // @Validation 기능

        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; //첫번째 페이지로 넘어가게됨


    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members); // Key와 Value값
        return"members/memberList"; // members(Directory) -> memberList
    }

}
