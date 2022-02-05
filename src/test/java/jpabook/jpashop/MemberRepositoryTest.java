package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;   // 필드에서 직접적으로 의존성 주입해줌


    @Test
    @Transactional  // transaciton을 수행하도록 만듦.
    public void testMember() throws Exception {
        // given
        Member member=new Member();
        member.setUsername("A");

        // when
        Long savedId=memberRepository.save(member);
        Member findMember=memberRepository.find(savedId);

        // then
        Assertions.assertThat(findMember).isEqualTo(member);
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

}