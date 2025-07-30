package tobyspring.splearn.application.provided;

import tobyspring.splearn.domain.Member;

public interface MemberFinder {

    /**
     * 회원을 조회한다
     * */
    Member find(Long memberId);
}
