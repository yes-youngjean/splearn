package tobyspring.splearn.domain;

/**
 * 이 인터페이스도 required로 가는 것이 맞으나,
 * 의존 방향은 반드시 어댑터 -> 애플리케이션 -> 도메인 방향으로 흘러야 하기에 domain 폴더 내에 위치해야 함
 * */
public interface PasswordEncoder {

    String encode(String password);

    boolean matches(String password, String passwordHash);
}
