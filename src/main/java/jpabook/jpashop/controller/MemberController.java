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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }
    // 폼을 전송한 이후 매커니즘 - DTO 와 비슷한 개념으로 설계된듯
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        // BindingResult를 통해서 뭔가를 입력을 덜해서 에러가 나면 멤버 폼으로 다시 가게한다.+ 빨간줄,@NotEmpty로 적용한 메세지 적용
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address=new Address(form.getCity(),form.getStreet(),form.getZipcode());

        Member member=new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }



}
