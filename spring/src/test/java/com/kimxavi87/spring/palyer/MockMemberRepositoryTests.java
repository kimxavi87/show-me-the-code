package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.entity.Member;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockMemberRepositoryTests {

    @Test
    public void givenMockRepository_whenSaveAndFind_thenSuccess() {
        String memberName = "Park-ji-sung";
        Member member = new Member(memberName);
        MemberRepository mockMemberRepository = mock(MemberRepository.class);
        when(mockMemberRepository.save(any()))
                .thenReturn(member);

        // todo : any말고 함수호출에 대한 특정 파라미터를 지정해줄수 있나?
        when(mockMemberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        Optional<Member> byId = mockMemberRepository.findById(100L);
        assertThat(byId.isPresent()).isTrue();
        Member foundMember = byId.get();
        assertThat(foundMember.getName()).isEqualTo(memberName);
    }
}
