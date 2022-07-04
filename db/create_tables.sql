-- TABLE 생성 --

--clients table
CREATE TABLE clients (
    client_id VARCHAR2(40 CHAR),
    client_pwd VARCHAR2(20 CHAR),
    client_cellphone_no VARCHAR2(15),
    client_nickname VARCHAR2(10 CHAR) NOT NULL,
    client_status_flag NUMBER(1) NOT NULL, -- 가입 = 1 / 탈퇴 = 0
    CONSTRAINT clients_pk PRIMARY KEY(client_id),
    CONSTRAINT clients_uk UNIQUE(client_nickname)
);

--diaries table
CREATE TABLE diaries (
    diary_no NUMBER(6), -- PK
    client_id VARCHAR2(40 CHAR) NOT NULL,
    diary_title VARCHAR2(30 CHAR) NOT NULL,  -- 한글로 30자 가능
    diary_writing_time DATE NOT NULL,
    diary_start_date DATE NOT NULL,
    diary_end_date DATE NOT NULL,
    diary_flag NUMBER(1) NOT NULL, -- 공개 = 1 / 비공개 = 0
    diary_view_cnt NUMBER(5) NOT NULL,
    diary_like_cnt NUMBER(5) NOT NULL,
    diary_delete_flag NUMBER(1) NOT NULL, -- 삭제안된상태 = 1 / 삭제된상태 = 0
    CONSTRAINT diaries_pk PRIMARY KEY(diary_no),
    CONSTRAINT diaries_clients_fk FOREIGN KEY(client_id) REFERENCES clients(client_id)
);

--comments table
CREATE TABLE comments(
    comment_no NUMBER(8) NOT NULL, -- PK
    diary_no NUMBER(6) NOT NULL, -- FK
    client_id VARCHAR2(40 CHAR) NOT NULL, -- FK
    comment_content VARCHAR2(300 CHAR) NOT NULL,
    comment_writing_time DATE NOT NULL,
    CONSTRAINT comments_pk PRIMARY KEY(comment_no, diary_no),
    CONSTRAINT comments_travels_fk FOREIGN KEY(diary_no) REFERENCES diaries(diary_no),
    CONSTRAINT comments_clients_fk FOREIGN KEY(client_id) REFERENCES clients(client_id)
);

-- likes table
CREATE TABLE likes(
    diary_no NUMBER(6), -- FK, PK
    client_id VARCHAR2(40 CHAR), -- FK , PK
    CONSTRAINT likes_pk PRIMARY KEY(diary_no, client_id),
    CONSTRAINT likes_clients_fk FOREIGN KEY(client_id) REFERENCES clients(client_id),
    CONSTRAINT likes_diaries_fk FOREIGN KEY(diary_no) REFERENCES diaries(diary_no)
);

-- sights table
CREATE TABLE sights(
    sight_no NUMBER NOT NULL,
    sight_name VARCHAR2(20 CHAR) NOT NULL,
    sight_addr VARCHAR2(30 CHAR),
    sight_id NUMBER NOT NULL,
    sight_category_name VARCHAR2(8 CHAR),
    CONSTRAINT sights_pk PRIMARY KEY(sight_no),
    CONSTRAINT sights_uk UNIQUE(sight_id)
);

-- routes rable
CREATE TABLE routes(
    diary_no NUMBER NOT NULL,
    route_no NUMBER NOT NULL,
    sight_no NUMBER NOT NULL,
    route_content VARCHAR2(500 CHAR),
    CONSTRAINT routes_pk PRIMARY KEY(diary_no, route_no),
    CONSTRAINT routes_sights_fk FOREIGN KEY(sight_no) REFERENCES sights(sight_no)
);

-- admins rable
CREATE TABLE admins(
    admin_id VARCHAR2(20 CHAR) NOT NULL,
    admin_pwd VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT admins_pk PRIMARY KEY(admin_id)
);


-- SEQUENCE 생성--

-- diary_no_seq
CREATE SEQUENCE diary_no_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE;

-- sight_no_seq
CREATE SEQUENCE sight_no_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE;



-- 계정 생성 (필요한지 모르겠음) --
CREATE USER admin_travel -- USER_ID 
IDENTIFIED BY travel; -- USER_PWD

GRANT CONNECT, RESOURCE TO admin_travel; -- CONNECT create session 권한이 있음
                                         -- RESOURCE create 트리거, 시퀀스, 타입, 프로시저, 테이블 등 8가지 권한이 부여되어있음
