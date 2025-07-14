package tobyspring.splearn.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

import static org.springframework.util.Assert.state;
import static tobyspring.splearn.domain.MemberStatus.*;

@Getter
@ToString
public class Member {
    String email;

    String nickname;

    String passwordHash;

    MemberStatus status;

    private Member(String email,
                   String nickname,
                   String passwordHash) {
        //requireNonNull => Null일 경우 Exception 던짐
        this.email = Objects.requireNonNull(email);
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);

        this.status = PENDING;
    }

    public static Member create(String email,
                                String nickname,
                                String password,
                                PasswordEncoder passwordEncoder) {
        return new Member(email, nickname, passwordEncoder.encode(password));
    }

    public void activate() {
//        if(this.status != MemberStatus.PENDING) throw new IllegalStateException("PENDING 상태가 아닙니다.");
        state(status == PENDING, "PENDING 상태가 아닙니다.");

        this.status = ACTIVE;
    }

    public void deactivate() {
        state(status == ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = DEACTIVATED;
    }
}
