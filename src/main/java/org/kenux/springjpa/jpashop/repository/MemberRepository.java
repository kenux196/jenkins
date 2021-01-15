package org.kenux.springjpa.jpashop.repository;

import org.kenux.springjpa.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/* Spring + JPA
@Repository
public class MemberRepository {

    @PersistenceContext // EntityManager 주입
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
 */

/**
 * Using Spring Data JPA
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name); // 쿼리 메소드 방식.
}