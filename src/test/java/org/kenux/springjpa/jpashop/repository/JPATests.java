/**
 * Project: B.IOT Cloud
 * <p>
 * Copyright (c) 2017 DMC R&D Center, SAMSUNG ELECTRONICS Co.,LTD.
 * (Maetan dong)129, Samsung-ro Yeongtong-gu, Suwon-si. Gyeonggi-do 443-742, Korea
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of Samsung Electronics, Inc.
 * ("Confidential Information"). You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered
 * into with Samsung Electronics.
 */
package org.kenux.springjpa.jpashop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kenux.springjpa.jpashop.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <pre>
 * 서비스 명   : jpashop
 * 패키지 명   : org.kenux.springjpa.jpashop.repository
 * 클래스 명   : JPATests
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 * @date 2021-01-13
 * @author skyun
 * @version 1.0.0
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATests {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void memberRepository_Test() {

        List<Member> members = memberRepository.findAll();

        log.info("멤버 수 = " + members.size());
    }
}
