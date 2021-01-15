package org.kenux.springjpa.jpashop.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kenux.springjpa.jpashop.domain.Member;
import org.kenux.springjpa.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입_테스트() throws Exception {
        // Given
        Member member = Member.builder()
                .name("윤상규")
                .build();

        // When
        Long saveId = memberService.join(member);

        // Then
        assertEquals(member, memberRepository.findById(saveId));
    }

    @Test(expected = IllegalStateException.class) // 지정한 예외가 발생해야 성공한다.
    public void 중복_회원_예외_테스트() {
        // Given
        Member member1 = Member.builder()
                .name("kenux")
                .build();

        Member member2 = Member.builder()
                .name("kenux")
                .build();

        // When
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다.

        // Then
        fail("예외가 발생해야 한다.");
    }
}
