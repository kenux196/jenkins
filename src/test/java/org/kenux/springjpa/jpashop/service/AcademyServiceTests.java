package org.kenux.springjpa.jpashop.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kenux.springjpa.jpashop.domain.Academy;
import org.kenux.springjpa.jpashop.domain.Subject;
import org.kenux.springjpa.jpashop.repository.AcademyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AcademyServiceTests {

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private AcademyService academyService;

    @After
    public void cleanAll() {
        academyRepository.deleteAll();
    }

    @Before
    public void setup() {
        List<Academy> academies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Academy academy = Academy.builder()
                    .name("강남스쿨" + i)
                    .build();

            academy.addSubject(Subject.builder().name("자바웹개발" + i).build());
            academy.addSubject(Subject.builder().name("파이썬자동화" + i).build());

            academies.add(academy);
        }
        academyRepository.saveAll(academies);
    }

    @Test
    public void Academy여러개를_조회시_Subject가_N1_쿼리가발생한다() throws Exception {
        //given
        List<String> subjectNames = academyService.findAllSubjectNames();

        //then
        assertThat(subjectNames.size(), is(10));
    }

    @Test
    public void Academy여러개를_조회시_Subject가_N1_해결1_fetchJoin() throws Exception {
        //given
        List<String> subjectNames = academyService.findAllJoinFetchSubjectNames();

        //then
        assertThat(subjectNames.size(), is(10));
    }

    @Test
    public void fetchJoin_Teacher까지_가져오기() throws Exception {
        log.info("모든 과목을 추출한다");
        List<Academy> academies = academyRepository.findAllWithTeacher();
        List<String> names = academies.stream()
                .map(Academy::getSubjects)
                .flatMap(Collection::stream)
                .map(Subject::getName)
                .distinct()
                .collect(Collectors.toList());

        log.info("kenux result = ");
        names.forEach(name -> log.info("result " + name));
    }

    @Test
    public void using_EntityGraph_N1해결() throws Exception {
        log.info("kenux test");
        List<Academy> academies = academyRepository.findAllEntityGraph();
        List<String> names = academies.stream()
                .map(Academy::getSubjects)
                .flatMap(Collection::stream)
                .map(Subject::getName)
                .distinct()
                .collect(Collectors.toList());

        log.info("kenux result = ");
        names.forEach(name -> log.info("result " + name));
    }

    @Test
    public void using_EntityGraph_N1해결_WithTeacherTest() throws Exception {
        log.info("kenux test");
        List<Academy> academies = academyRepository.findAllEntityGraphWithTeacher();

        List<String> names = academies.stream()
                .map(Academy::getSubjects)
                .flatMap(Collection::stream)
                .map(Subject::getName)
                .distinct()
                .collect(Collectors.toList());

        log.info("kenux result = ");
        names.forEach(name -> log.info("result " + name));
    }

    @Test
    public void Academy여러개를_joinFetch로_가져온다() throws Exception {
        //given
        List<Academy> academies = academyRepository.findAllJoinFetch();

        //then
        assertThat(academies.size(), is(20)); // 20개가 조회!?
    }
}
