drop table if exists calendar;
drop table if exists couple;
drop table if exists member;

CREATE TABLE couple (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    boy_id BIGINT,
    girl_id BIGINT,
    meet_day date,
    invitation_code VARCHAR(255),
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_couple_boy_id (boy_id),
    INDEX idx_couple_girl_id (girl_id),
    INDEX idx_couple_invitation_code (invitation_code)
);

CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    couple_id BIGINT,
    sex VARCHAR(7),
    name VARCHAR(15),
    nick_name VARCHAR(50),
    birthday DATE,
    mbti VARCHAR(7),
    calendar_color VARCHAR(31),
    image_url VARCHAR(255),
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_couple_id(couple_id)
);

CREATE TABLE calendar(
     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     start_date DATE NOT NULL,
     end_date DATE,
     member_id BIGINT,
     couple_id BIGINT NOT NULL,
     schedule_type VARCHAR(31) NOT NULL,
     schedule_details VARCHAR(255) NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     INDEX idx_calendar_member_id (member_id),
     INDEX idx_calendar_start_date (start_date),
     INDEX idx_calendar_end_Date (end_date),
     INDEX idx_calendar_couple_id (couple_id)
);

insert into member (couple_id, sex, calendar_color) values (1, 'MALE', 'RED');
insert into member (couple_id, sex, calendar_color) values (1, 'FEMALE', 'BLUE');
insert into couple (boy_id, girl_id) values (1, 2);

insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-05', 1, 'DATE', '영화');
insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-06', 1, 'DATE', '영화');
insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-07', 1, 'DATE', '영화');
insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-08', 1, 'DATE', '영화');
insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-09', 1, 'DATE', '영화');
insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-10', 1, 'DATE', '영화');
insert into calendar (start_date, couple_id, schedule_type, schedule_details) values ('2023-11-11', 1, 'DATE', '영화');