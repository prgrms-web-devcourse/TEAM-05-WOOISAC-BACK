package com.lovely4k.backend.couple;

import com.lovely4k.backend.member.Sex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CoupleTest {

    @Test
    @DisplayName("여자친구를 등록한다.")
    void registerGirlId() throws Exception {
        //given
        Couple couple = Couple.builder()
                .boyId(1L)
                .invitationCode("sampleInvitationCode")
                .build();

        //when
        couple.registerGirlId(2L);

        //then
        assertThat(couple.getGirlId())
                .isEqualTo(2L);
    }

    @Test
    @DisplayName("남자친구를 등록한다.")
    void registerBoyId() throws Exception {
        //given
        Couple couple = Couple.builder()
                .girlId(1L)
                .invitationCode("sampleInvitationCode")
                .build();

        //when
        couple.registerBoyId(2L);

        //then
        assertThat(couple.getBoyId())
                .isEqualTo(2L);
    }

    @Test
    @DisplayName("커플 프로필을 수정한다.")
    void update() throws Exception {
        //given
        Couple couple = Couple.builder()
                .boyId(1L)
                .girlId(2L)
                .invitationCode("sampleInvitationCode")
                .build();

        LocalDate meetDay = LocalDate.of(2023, 10, 29);

        //when
        couple.update(meetDay);

        //then
        assertThat(couple.getMeetDay())
                .isEqualTo(meetDay);
    }
  
    @DisplayName("hasAuthority를 통해 couple에 대한 권한이 있는 지 검증 할 수 있다.")
    @CsvSource(value = {"1,true", "2,true", "3,false"})
    @ParameterizedTest
    void hasAuthority(Long memberId, boolean expected) {
        // given
        Couple couple = Couple.builder()
                .boyId(1L)
                .girlId(2L)
                .invitationCode("sampleInvitationCode")
                .build();

        // when
        boolean result = couple.hasAuthority(memberId);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("커플의 기본 온도는 0도이다. (남자가 커플을 만드는 경우)")
    @Test
    void couple_temperature_boy() {
        // given
        Long requestMemberId = 1L;
        Sex sex = Sex.MALE;
        String invitationCode = "test-invitation-code";

        // when
        Couple couple = Couple.create(requestMemberId, sex, invitationCode);

        // then
        assertAll(
            () -> assertThat(couple.getTemperature()).isEqualTo(0f),
            () -> assertThat(couple.getBoyId()).isNotNull()
        );
    }

    @DisplayName("커플의 기본 온도는 0도이다. (여자가 커플을 만드는 경우)")
    @Test
    void couple_temperature_girl() {
        // given
        Long requestMemberId = 1L;
        Sex sex = Sex.FEMALE;
        String invitationCode = "test-invitation-code";

        // when
        Couple couple = Couple.create(requestMemberId, sex, invitationCode);

        // then
        assertAll(
            () -> assertThat(couple.getTemperature()).isEqualTo(0f),
            () -> assertThat(couple.getGirlId()).isNotNull()
        );
    }

    @DisplayName("increaseTemperature를 통해 커플의 온도를 올릴 수 있다.")
    @Test
    void increaseTemperature() {
        // given
        Couple couple = Couple.builder()
            .boyId(1L)
            .girlId(2L)
            .meetDay(LocalDate.of(2020, 10, 20))
            .invitationCode("test-invitation-code")
            .temperature(0f)
            .build();

        assertThat(couple.getTemperature()).isEqualTo(0f);
        // when
        couple.increaseTemperature();

        // then
        assertThat(couple.getTemperature()).isEqualTo(1f);
    }

    @DisplayName("30일을 초과했을 경우 isExpired의 결과값은 true")
    @Test
    void isExpired_true() {
        Couple couple = Couple.builder()
            .boyId(1L)
            .girlId(2L)
            .meetDay(LocalDate.of(2020, 10, 20))
            .invitationCode("sampleInvitationCode")
            .deleted(true)
            .deletedDate(LocalDate.of(2022, 7, 1))
            .build();

        // when
        boolean result = couple.isExpired(LocalDate.of(2022, 8, 1));

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("커플의 온도는 최대 100도 이다.")
    @Test
    void increaseTemperature_max100() {
        Couple couple = Couple.builder()
            .boyId(1L)
            .girlId(2L)
            .meetDay(LocalDate.of(2020, 10, 20))
            .invitationCode("test-invitation-code")
            .temperature(100f)
            .build();

        assertThat(couple.getTemperature()).isEqualTo(100f);

        // when
        couple.increaseTemperature();

        // then
        assertThat(couple.getTemperature()).isEqualTo(100f);
    }

    @DisplayName("30일을 초과하지 않았을 경우 isExpired의 결과값은 false")
    @Test
    void isExpired_false() {
        // given
        Couple couple = Couple.builder()
                .boyId(1L)
                .girlId(2L)
                .invitationCode("sampleInvitationCode")
                .deleted(true)
                .deletedDate(LocalDate.of(2022, 7, 1))
                .build();

        // when
        boolean result = couple.isExpired(LocalDate.of(2022, 7,31));

        // then
        assertThat(result).isFalse();
    }
}
