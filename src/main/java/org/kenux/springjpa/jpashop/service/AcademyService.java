package org.kenux.springjpa.jpashop.service;

import lombok.extern.slf4j.Slf4j;
import org.kenux.springjpa.jpashop.domain.Academy;
import org.kenux.springjpa.jpashop.domain.Subject;
import org.kenux.springjpa.jpashop.repository.AcademyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AcademyService {

    private final AcademyRepository academyRepository;

    public AcademyService(AcademyRepository academyRepository) {
        this.academyRepository = academyRepository;
    }

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNames(){
        return extractSubjectNames(academyRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<String> findAllJoinFetchSubjectNames() {
        return extractSubjectNames(academyRepository.findAllJoinFetch());
    }

    /**
     * Lazy Load를 수행하기 위해 메소드를 별도로 생성
     */
    private List<String> extractSubjectNames(List<Academy> academies){
        log.info(">>>>>>>>[모든 과목을 추출한다]<<<<<<<<<");
//        log.info("Academy Size : {}", academies.size());

        return academies.stream()
                        .map(Academy::getSubjects)
                        .flatMap(Collection::stream)
                        .map(Subject::getName)
                        .distinct()
                        .collect(Collectors.toList());

//        return academies.stream()
//                .map(a -> a.getSubjects().get(0).getName())
//                .collect(Collectors.toList());
    }
}
