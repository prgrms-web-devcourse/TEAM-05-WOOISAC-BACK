package com.lovely4k.backend.couple;

import com.lovely4k.backend.common.jpa.BaseTimeEntity;
import com.lovely4k.backend.member.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Couple extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "boy_id")
    private Long boyId;

    @Column(name = "girl_id")
    private Long girlId;

    @Column(name = "meet_day")
    private LocalDate meetDay;

    @Column(name = "invitation_code")
    private String invitationCode;

    @Column(name = "temperature")
    private Float temperature;

    @Builder
    public Couple(Long boyId, Long girlId, LocalDate meetDay, String invitationCode, Float temperature) {
        this.boyId = boyId;
        this.girlId = girlId;
        this.meetDay = meetDay;
        this.invitationCode = invitationCode;
        this.temperature = temperature;
    }

    public static Couple create(Long requestedMemberId, Sex sex, String invitationCode) {
        if (sex == Sex.MALE) {
            return createBoy(requestedMemberId, invitationCode);
        } else {
            return createGirl(requestedMemberId, invitationCode);
        }
    }

    private static Couple createBoy(Long requestedMemberId, String invitationCode) {
        return Couple.builder()
                .boyId(requestedMemberId)
                .girlId(null)
                .meetDay(null)
                .invitationCode(invitationCode)
                .temperature(0.0f)
                .build();
    }

    private static Couple createGirl(Long requestedMemberId, String invitationCode) {
        return Couple.builder()
                .boyId(null)
                .girlId(requestedMemberId)
                .meetDay(null)
                .invitationCode(invitationCode)
                .temperature(0.0f)
                .build();
    }

    public void registerGirlId(Long receivedMemberId) {
        this.girlId = receivedMemberId;
    }

    public void registerBoyId(Long receivedMemberId) {
        this.boyId = receivedMemberId;
    }

    public void update(LocalDate meetDay) {
        this.meetDay = meetDay;
    }
}
