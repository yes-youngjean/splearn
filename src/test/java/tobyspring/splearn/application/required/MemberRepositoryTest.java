package tobyspring.splearn.application.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThat(member.getId()).isNull();

        //save 뒤 persistence 상태
        memberRepository.save(member);
        assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }

    @Test
    void duplicateEmailFail(){
        Member member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member1);

        Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(()->{
            memberRepository.save(member2);
            //@NaturalId => 식별자로서 중복을 허용하지 않는 고유한 값
        }).isInstanceOf(DataIntegrityViolationException.class);

    }
}