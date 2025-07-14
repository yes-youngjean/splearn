package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setMember(){
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        this.member = Member.create("ratee@naver.com", "Ratee", "secret", passwordEncoder);
    }

    @Test
    void createMember() {

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

}