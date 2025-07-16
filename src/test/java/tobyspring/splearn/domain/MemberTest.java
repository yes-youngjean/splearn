package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setMember(){
        this.passwordEncoder = createPasswordEncoder();
        this.member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }


    @Test
    void registerMember() {

        assertThat(member.getStatus().equals(MemberStatus.PENDING));
    }


    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus().equals(MemberStatus.ACTIVE));
    }


    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }


    @Test
    void deactivate(){
        member.activate();

        member.deactivate();

        assertThat(member.getStatus().equals(MemberStatus.DEACTIVATED));
    }


    @Test
    void deactivatedFail(){
        //(1) pending -> deactive 시 예외
        assertThatThrownBy(()->{
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        //(2) deactivate -> deactivate 시 예외
        assertThatThrownBy(()->{
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);
    }


    @Test
    void verifyPassword(){
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();

    }


    @Test
    void changeNickname(){
        assertThat(member.getNickname()).isEqualTo("Ratee");

        member.changeNickname("Charlie");

        assertThat(member.getNickname()).isEqualTo("Charlie");
    }


    @Test
    void changePassword(){
        member.changePassword("verysecret", passwordEncoder);

        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }


    @Test
    void isActive(){
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }


    @Test
    void invalidEmail(){
        assertThatThrownBy(()->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);

    }
}