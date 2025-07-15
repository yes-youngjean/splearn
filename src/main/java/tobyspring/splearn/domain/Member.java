package tobyspring.splearn.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.*;
import static org.springframework.util.Assert.state;
import static tobyspring.splearn.domain.MemberStatus.*;

@Getter
@ToString
public class Member {
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;
    //외부에서는 사용X, 내부에서만 사용
    private Member() {
    }

    //Static Factory Method 생성
    public static Member create(MemberCreateRequest createRequest,
                                PasswordEncoder passwordEncoder) {
        //기본생성자
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = PENDING;

        return member;
    }


    public void activate() {
//        if(this.status != MemberStatus.PENDING) throw new IllegalStateException("PENDING 상태가 아닙니다.");
        state(this.status == PENDING, "PENDING 상태가 아닙니다.");

        this.status = ACTIVE;
    }


    public void deactivate() {
        state(this.status == ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = DEACTIVATED;
    }

    public boolean verifyPassword(String password,
                                  PasswordEncoder passwordEncoder) {
         return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == ACTIVE;
    }
}
