package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void equality(){
        var email1 = new Email("test@test.com");
        var email2 = new Email("test@test.com");

        // record는 값 객체(Value Object)로 설계되어 있음
        // 필드 값이 같으면 equals()도 true
        // 서로 다른 객체지만 같은 값이면 같은 객체로 취급함
        assertEquals(email1, email2);
    }

}