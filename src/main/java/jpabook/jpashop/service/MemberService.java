package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly=true)  // 데이터 변경이나 그런것들은 트랜잭션안에서 진행이 되어야하는 부분이기때문에 꼭 넣어준다
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired  // 생성자 주입
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    // 회원 가입
    @Transactional    // 클래스 전체가 readOnly 적용되어있기때문에 데이터변경되는 트랜잭션은 따로 @Transactional 처리
    public Long Join(Member member){
        validateDuplicateMember(member); // 중복회원 검증(중복되는 회원이 등록되는것을 막음)
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        // 중복회원이면 Exception 터뜨리도록
       List<Member> findMembers=memberRepository.findByName(member.getName());
       if(!findMembers.isEmpty()){
           throw new IllegalStateException("이미 존재하는 회원 입니다");
       }
    }
    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    // 회원 한 건만 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
