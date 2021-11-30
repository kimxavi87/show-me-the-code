package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockMemberRepositoryTests {

    @Test
    public void givenMockRepository_whenSaveAndFind_thenSuccess() {
        String memberName = "Park-ji-sung";
        Member member = new Member(memberName);
        MemberRepository mockMemberRepository = mock(MemberRepository.class);
        when(mockMemberRepository.save(eq(member)))
                .thenReturn(member);

        // any말고 함수호출에 대한 특정 파라미터를 지정해줄수 있나?
        // => eq() 사용해서 파라미터 넣어줄 수 있음, eq("park")
        when(mockMemberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        // void로 된 메서드엔 doNothing().when()
        // 예외 던지고 싶으면 doThrow().when()

        Optional<Member> byId = mockMemberRepository.findById(100L);
        assertThat(byId.isPresent()).isTrue();
        Member foundMember = byId.get();
        assertThat(foundMember.getName()).isEqualTo(memberName);
    }
}
