package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tobyspring.splearn.domain.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MemberRegisterTest {

    @Autowired
    private MemberRegister memberRegister;


    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }


    @Test
    void activate(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        member = memberRegister.active(member.getId());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }


    @Test
    void memberRegisterRequestFail() {
        invalidRequest(new MemberRegisterRequest("email@email.com", "Tom", "longosinosknlw"));
        invalidRequest(new MemberRegisterRequest("email@email.com", "ccharlaisfnleiosneonkkie", "secret"));
    }

    private void invalidRequest(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
