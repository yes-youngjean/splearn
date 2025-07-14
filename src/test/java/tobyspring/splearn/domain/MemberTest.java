package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("ratee@naver.com", "Ratee", "secret");

        assertThat(member.getStatus().equals(MemberStatus.PENDING));
    }

    @Test
    void constructorNullCheck() {//check email null value
        assertThatThrownBy(() -> new Member(null, "Ratee", "secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        var member = new Member("ratee@naver.com", "Ratee", "secret");
        member.activate();

        assertThat(member.getStatus().equals(MemberStatus.ACTIVE));
    }

    @Test
    void activateFail() {
        var member = new Member("ratee@naver.com", "Ratee", "secret");
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate(){
        var member = new Member("ratee@naver.com", "Ratee", "secret");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus().equals(MemberStatus.DEACTIVATED));
    }

    @Test
    void deactivatedFail(){
        var member = new Member("ratee@naver.com", "Ratee", "secret");
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
}