package tobyspring.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.*;
import static org.springframework.util.Assert.state;
import static tobyspring.splearn.domain.MemberStatus.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가
    private Long id;

    @Embedded
    private Email email;

    private String nickname;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    //Static Factory Method 생성
    public static Member register(MemberRegisterRequest registerRequest,
                                  PasswordEncoder passwordEncoder) {
        //by 기본생성자
        Member member = new Member();

        member.email = new Email(registerRequest.email());
        member.nickname = requireNonNull(registerRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(registerRequest.password()));

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
