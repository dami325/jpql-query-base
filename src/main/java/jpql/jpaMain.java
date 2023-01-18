package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class jpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

                Team teamA = new Team();
                teamA.setName("팀A");
                em.persist(teamA);

                Team teamB = new Team();
                teamB.setName("팀B");
                em.persist(teamB);

                Member member1 = new Member();
                member1.setUsername("회원1");
                member1.setTeam(teamA);
                em.persist(member1);

                Member member2 = new Member();
                member2.setUsername("회원2");
                member2.setTeam(teamA);
                em.persist(member2);

                Member member3 = new Member();
                member3.setUsername("회원3");
                member3.setTeam(teamB);
                em.persist(member3);


            em.flush();
            em.clear();

//            String query = "SELECT m.username from Member m"; 상태 필드 더이상 탐색 x
//            String query = "SELECT m.team from Member m"; 단일 값 연관 경로 : 묵시적 내부 조인 발생(m.team.name) join쿼리 날라감
            String query = "SELECT m from Member m join fetch m.team";// 컬렉션 값 연관 경로 : 묵시적 내부 조인 발생, 탐색x , from 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
