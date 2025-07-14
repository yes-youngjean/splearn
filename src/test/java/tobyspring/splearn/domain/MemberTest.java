//package tobyspring.splearn.domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class MemberTest {
//
//    Member member;
//    @BeforeEach
//    void setUp() {
//        Member.create("123@naver.com", "nick", "secret", new PasswordEncoder() {
//            @Override
//            public String encode(String password) {
//                return "";
//            }
//
//            @Override
//            public boolean matches(String password, String passwordHash) {
//                return false;
//            }
//        });
//    }
//
//    @Test
//    void createMember() {
//        //신규 등록 시 등록 대기 상태 TEST
//        assertThat(member.getStatus().equals(MemberStatus.PENDING));
//    }
//
//    @Test
//    void constructorNullCheck() {
//        //에러가 날 경우에만 테스트 성공
//        assertThatThrownBy(() -> Member.create(null, "nick", "secret", null))
//                .isInstanceOf(NullPointerException.class);
//    }
//
//    @Test
//    void active() {
//        member.activate();
//        ;
//
//        assertThat(member.getStatus().equals(MemberStatus.ACTIVE));
//    }
//
//    @Test
//    void activateFail() {
//        member.activate();
//        //activate PENDING 상태에서만 가능함
//        assertThatThrownBy(() -> member.activate())
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void deactivate() {
//        member.activate();
//
//        member.deactivate();
//
//        assertThat(member.getStatus().equals(MemberStatus.DEACTIVATED));
//    }
//
//    @Test
//    void deactivateFail() {
//        //deactivate는 ACTIVE 상태에서만 가능함
//        assertThatThrownBy(() -> member.deactivate())
//                .isInstanceOf(IllegalStateException.class);
//
//        member.activate();
//        member.deactivate();
//
//        assertThatThrownBy(() -> member.deactivate())
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//}