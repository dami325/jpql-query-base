package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class jpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)     // 타입 정보가 명확할때
                    .setParameter("username", "member1")
                    .getSingleResult();

            System.out.println("result = " + result.getUsername());
//
//            Member result = query.getSingleResult();// 반환타입이 하나일때, 단 결과가 없다면 NoResultException 발생, 2개이상이면 NonUniqueResultException 발생
//            //Spring Data JPA -> 결과가 없어도 Exception 안터짐
//            System.out.println("result = " + result);
//
//            List<Member> resultList = query.getResultList(); // 반환타입이 여러개일때 List
//
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }
////            Query query1 = em.createQuery("select m.username, m.age from Member m"); // 타입 정보가 명확하지 않을때


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
