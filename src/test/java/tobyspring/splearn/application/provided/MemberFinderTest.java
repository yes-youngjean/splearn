package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MemberFinderTest {

    @Autowired
    MemberFinder memberFinder;
    @Autowired
    MemberRegister memberRegister;
    @Autowired
    EntityManager entityManager;

    @Test
    void find(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        Member found = memberFinder.find(member.getId());

        assertThat(member.getId()).isEqualTo(found.getId());
    }
}