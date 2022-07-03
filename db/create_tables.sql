-- TABLE 생성 --

--clients table
CREATE TABLE clients (
    id VARCHAR2(20 CHAR) NOT NULL,
    pwd VARCHAR2(20 CHAR) NOT NULL,
    client_roadaddr VARCHAR2(20 CHAR) NOT NULL,
    client_addr VARCHAR2(20 CHAR) NOT NULL,
    client_cellphone_no VARCHAR2(15) NOT NULL,
    nickname VARCHAR2(10 CHAR) NOT NULL,
    status_flag NUMBER(1) NOT NULL, -- 가입 = 1 / 탈퇴 = 0
    CONSTRAINT clients_pk PRIMARY KEY(id),
    CONSTRAINT clients_uk UNIQUE(nickname)
);
--시험용데이터
INSERT INTO clients 
     VALUES ('koreaman@gmail.com', 'asd11','성남시 어딘가', '830호', '010-1111-2234','한국남자1', 1);
INSERT INTO clients 
     VALUES clients ('japanman@gmail.com', 'asd12','성남시 어딘가', '831호', '010-1112-2234','도로 주소 중복', 1); -- 도로 주소 중복
INSERT INTO clients 
     VALUES ('chinaman@gmail.com', 'asd13','성남시 어딘가1', '832호', '010-1113-2234','한국남자3', 1);
INSERT INTO clients 
     VALUES ('americaman@gmail.com', 'asd14','성남시 어딘가2', '833호', '010-1114-2234','한국남자4', 1);
INSERT INTO clients 
     VALUES ('koreawoman@gmail.com', 'asd15','성남시 어딘가3', '834호', '010-1115-2234','한국남자5', 1);
INSERT INTO clients 
     VALUES ('japanwoman@gmail.com', 'asd16','성남시 어딘가4', '835호', '010-1116-2234','한국남자6', 1);
INSERT INTO clients 
     VALUES ('chinawoman@gmail.com', 'asd17','성남시 어딘가5', '836호', '010-1117-2234','한국남자7', 1);
INSERT INTO clients 
     VALUES ('americawoman@gmail.com', 'asd18','성남시 어딘가6', '837호', '010-1118-2234','한국남자8', 1);
INSERT INTO clients 
     VALUES ('americawoman@naver.com', 'asd19','성남시 어딘가7', '838호', '010-1119-2234','김정은', 1);
INSERT INTO clients 
     VALUES ('americaman@daum.net', 'asd11','성남시 어딘가8', '839호', '010-1110-2234','비번 중복', 1); -- 비번 중복
INSERT INTO clients 
     VALUES ('koreaman@naver.com', 'asd20','성남시 어딘가9', '840호', '010-1111-2234','전화 번호 중복', 1); -- 전화 번호 중복
INSERT INTO clients 
     VALUES ('koreawoman@naver.com', 'asd21','성남시 어딘가0', '830호', '010-1121-2235','상세 주소 중복', 1); -- 상세 주소 중복
INSERT INTO clients 
     VALUES ('koreawoman@daum.net', 'asd22','성남시 어딘가1', '841호', '010-1121-2236','아아아', 1);
INSERT INTO clients 
     VALUES ('japanwoman@naver.com', 'asd23','성남시 어딘가2', '842호', '010-1121-2237','한국남자1', 1); -- 닉네임 중복
INSERT INTO clients 
     VALUES ('japanwoman@naver.com', 'asd23','성남시 어딘가2', '842호', '010-1121-2237','한국남자1', 1); -- 아이디 중복 (에러 예상)


--diaries table
CREATE TABLE diaries (
    diary_no NUMBER(6) NOT NULL, -- PK
    id VARCHAR2(20 CHAR) NOT NULL, -- FK 20자리까지 가능
    diary_title VARCHAR2(30 CHAR) NOT NULL,  -- 한글로 30자 가능
    diary_writing_time DATE NOT NULL,
    diary_start_date DATE NOT NULL,
    diary_end_date DATE NOT NULL,
    diary_flag NUMBER(1) NOT NULL, -- 공개 = 1 / 비공개 = 0
    view_cnt NUMBER(5) NOT NULL,
    like_cnt NUMBER(5) NOT NULL,
    CONSTRAINT diaries_pk PRIMARY KEY(diary_no),
    CONSTRAINT diaries_clients_fk FOREIGN KEY(id) REFERENCES clients(id)
);
--시험용데이터
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@gmail.com', '즐거운 서울로 떠나요',
             TO_DATE('20220622 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200710','yyyy-mm-dd'), TO_DATE('20200712','yyyy-mm-dd'),
             1, 1523,  0);
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'japanman@gmail.com', '즐거운 부산으로 떠나요',
             TO_DATE('20220623 102311','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200711','yyyy-mm-dd'), TO_DATE('20200714','yyyy-mm-dd'),
             1, 1525,  1);

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'chinaman@gmail.com', '즐거운 대구로 떠나요',
             TO_DATE('20220624 102411','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200715','yyyy-mm-dd'), TO_DATE('20200719','yyyy-mm-dd'),
             1, 15235,  4); -- 공개 여부 중복시

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'americaman@gmail.com', '즐거운 광주으로 떠나요',
             TO_DATE('20220625 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200801','yyyy-mm-dd'), TO_DATE('20200820','yyyy-mm-dd'),
             1, 15231, 5); 

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreawoman@gmail.com', '즐거운 인천으로 떠나요',
             TO_DATE('20220626 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200101','yyyy-mm-dd'), TO_DATE('20200320','yyyy-mm-dd'),
             1, 15231,  6);

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'japanwoman@gmail.com', '즐거운 대전으로 떠나요',
             TO_DATE('20220626 202011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200201','yyyy-mm-dd'), TO_DATE('20200220','yyyy-mm-dd'),
             1, 15231, 0);

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'chinawoman@gmail.com', '즐거운 울산으로 떠나요',
             TO_DATE('20220627 222011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200301','yyyy-mm-dd'), TO_DATE('20200420','yyyy-mm-dd'),
             1, 15231, 1);

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'americawoman@gmail.com', '즐거운 세종으로 떠나요',
             TO_DATE('20220629 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200401','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 15231, 2);

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'americawoman@gmail.com', '즐거운 경남으로 떠나요',
             TO_DATE('20220629 112011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200402','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 15251, 9,3); -- 아이디 중복 인 경우

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'americawoman@naver.com', '즐거운 경남으로 떠나요',
             TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200403','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1521, 5); -- 제목 중복 인 경우

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'americaman@daum.net', '즐거운 경북으로 떠나요',
             TO_DATE('20220629 152011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1522,  6);  

INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요',
             TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1231, 7); -- 여행기간이 같을 경우
                        
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요',
             TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1231, 8); -- 뷰 수가 같을 경우
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요2',
             TO_DATE('20210601 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1031, 1);                          
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요3',
             TO_DATE('20210602 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1031, 11); 
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요3',
             TO_DATE('20210601 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1033, 12); 
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요4',
             TO_DATE('20210603 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200402','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1031, 1);
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요5',
             TO_DATE('20200601 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1031, 1); 
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요2',
             TO_DATE('20200601 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1031, 1);      
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요3',
             TO_DATE('20200602 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1001, 1);       
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요4',
             TO_DATE('20210501 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1031, 1);                    
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요5',
             TO_DATE('20210502 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1031, 3);
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요6',
             TO_DATE('20210801 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1011, 13);
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요7',
             TO_DATE('20210509 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1031, 11);             
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요8',
             TO_DATE('20211101 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200405','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             1, 1931, 13);
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요9',
             TO_DATE('20210521 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200505','yyyy-mm-dd'), TO_DATE('20200510','yyyy-mm-dd'),
             1, 2031, 41);    
INSERT INTO diaries
     VALUES (diary_no_seq.NEXTVAL, 'man@naver.com', '즐거운 전북으로 떠나요',
             TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
             0, 1231, 9); -- id가 client 테이블에 없을 경우 (에러 예상)             
             
--comments table
CREATE TABLE comments(
    comment_no NUMBER(8) NOT NULL, -- PK
    diary_no NUMBER(6) NOT NULL, -- FK
    comment_content VARCHAR2(300 CHAR) NOT NULL,
    comment_writing_time DATE NOT NULL,
    CONSTRAINT comments_pk PRIMARY KEY(comment_no, diary_no),
    CONSTRAINT comments_travels_fk FOREIGN KEY(diary_no) REFERENCES diaries(diary_no)
);
--시험용데이터
INSERT INTO comments
     VALUES (1, 1, '샘플데이터어', TO_DATE('20200622 121011', 'yyy-mm-dd hh24:mi:ss'));
INSERT INTO comments
     VALUES (1, 2, '샘플데이터어', TO_DATE('20200624 121011', 'yyy-mm-dd hh24:mi:ss')); -- 댓글 내용 중복
INSERT INTO comments
     VALUES (1, 3, '샘플데이터어어', TO_DATE('20200622 121011', 'yyy-mm-dd hh24:mi:ss')); -- 글 작성 일자보다 일찍 댓글
INSERT INTO comments
     VALUES (1, 1, '샘플데이터어어어', TO_DATE('20200622 121013', 'yyy-mm-dd hh24:mi:ss')); -- PK 중복


-- likes table
CREATE TABLE likes(
    diary_no NUMBER(6), -- FK, PK
    id VARCHAR2(20 CHAR), -- FK , PK
    CONSTRAINT likes_pk PRIMARY KEY(diary_no, id),
    CONSTRAINT likes_clients_fk FOREIGN KEY(id) REFERENCES clients(id),
    CONSTRAINT likes_diaries_fk FOREIGN KEY(diary_no) REFERENCES diaries(diary_no)
);
--시험용데이터
INSERT INTO likes
     VALUES (1, 'koreaman@gmail.com');
INSERT INTO likes
     VALUES (2, 'japanman@gmail.com'); 
INSERT INTO likes
     VALUES (3, 'chinaman@gmail.com');
INSERT INTO likes
     VALUES (4, 'americaman@gmail.com'); -- PK LIKE_NO 중복
INSERT INTO likes
     VALUES (222, 'koreaman@gmail.com'); -- PK LIKE_ID 중복

-- regions table
CREATE TABLE regions(
    region_no NUMBER NOT NULL, -- PK
    region_name VARCHAR2(8 CHAR) NOT NULL,
    CONSTRAINT regions_pk PRIMARY KEY(region_no)
);
-- 실제 데이터 예정
INSERT INTO regions
     VALUES (1, '서울');
INSERT INTO regions
     VALUES (2, '부산');
INSERT INTO regions
     VALUES (3, '대구');
INSERT INTO regions
     VALUES (4, '인천');
INSERT INTO regions
     VALUES (5, '광주');
INSERT INTO regions
     VALUES (6, '대전');
INSERT INTO regions
     VALUES (7, '울산');
INSERT INTO regions
     VALUES (8, '세종특별자치시');
INSERT INTO regions
     VALUES (9, '경기');
INSERT INTO regions
     VALUES (10, '충북');
INSERT INTO regions
     VALUES (11, '충남');
INSERT INTO regions
     VALUES (12, '전북');
INSERT INTO regions
     VALUES (13, '전남');
INSERT INTO regions
     VALUES (14, '경남');
INSERT INTO regions
     VALUES (15, '경북');
INSERT INTO regions
     VALUES (16, '제주특별자치도');


-- sights table
CREATE TABLE sights(
    sight_no NUMBER NOT NULL,
    region_no NUMBER NOT NULL,
    sight_name VARCHAR2(20 CHAR) NOT NULL,
    sight_addr VARCHAR2(30 CHAR),
    sight_id NUMBER NOT NULL,
    sight_category_name VARCHAR2(8 CHAR),
    CONSTRAINT sights_pk PRIMARY KEY(sight_no),
    CONSTRAINT sights_uk UNIQUE(sight_id),
    CONSTRAINT sights_regions_fk FOREIGN KEY(region_no) REFERENCES regions(region_no)
);
--시험용데이터
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 1, '경복궁', '서울 종로구 사직로 161 경복궁', 100001, '문화시설'); -- 정상
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 1, '남산', '서울 중구 회현동1가', 100001, '약국'); -- SIGHT_ID 중복
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 17, '남산왕돈까스', '충북 청주시 상당구 용암북로 30 1층', 100003, '음식점'); -- 범위(1~16)를 벗어나는 REGION_ID 추가
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 2, '남산왕돈까스 2호점', '충북 청주시 상당구 용암북로 30 1층', 100004, '음식점'); --  주소 중복
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 2, '남산왕돈까스 서울점', '서울특별시 동작구 용암북로 30 1층', 100005, '음식점'); --  정상
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 12, '집', '경기 성남시 분당구 벌말로', 100006, '새로운카테고리'); --  카카오맵 API에 없는 SIGHT_NAME
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 12, '율동공원', '성남시 분당구 문정로 72', 100007, '문화시설'); --  주소에 광역자지단체명이 없는 경우
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 12, '율동공원', '경기도 광주시 초월로 70', 100008, '문화시설'); --  관광지 이름이 같은 경우
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 1, '여행지9', '서울 종로구 종로', 100009, '문화시설'); -- 정상
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 11, '여행지10', '서울 종로구 북로', 100010, '음식점'); -- 정상
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 10, '여행지11', '서울 종로구 남로', 100011, '중개업소'); -- 카카오맵 API에 있는 SIGHT_CODE지만 관광과 상관없는 경우
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 10, '여행지12', '서울 종로구 서로', 100012, '중개업소'); -- 정상
INSERT INTO sights
     VALUES (sight_no_seq.NEXTVAL, 7, '여행지13', '서울 종로구 서서로', 100013, '중개업소'); -- 정상


-- routes rable
CREATE TABLE routes(
    diary_no NUMBER NOT NULL,
    route_no NUMBER NOT NULL,
    sight_no NUMBER NOT NULL,
    route_content VARCHAR2(500 CHAR),
    CONSTRAINT routes_pk PRIMARY KEY(diary_no, route_no),
    CONSTRAINT routes_sights_fk FOREIGN KEY(sight_no) REFERENCES sights(sight_no)
);
--시험용데이터
INSERT INTO routes
     VALUES (1, 1, 1, '경복궁갔다옴'); -- 정상
INSERT INTO routes
     VALUES (1, 2, 9999, '어디갔다옴'); -- SIGHT_NO가 없는 관광지를 루트에 추가
INSERT INTO routes
     VALUES (1, 3, 3, '남산왕돈까스갔다옴'); -- 날짜가 과거가 아니라 미래인 경우
INSERT INTO routes
     VALUES (1, 4, 4, '남산왕돈까스2호점갔다옴'); -- 너무 옛날인 경우
INSERT INTO routes
     VALUES (1, 5, 4, '남산왕돈까스2호점갔다옴'); -- 같은 다이어리 안에 같은 관광지를 루트에 추가하는 경우
INSERT INTO routes
     VALUES (2, 1, 1, '경복궁갔다옴'); -- 정상
INSERT INTO routes
     VALUES (2, 2, 2, '남산갔다옴'); -- 여행 2일차
INSERT INTO routes
     VALUES (2, 3, 3, '남산왕돈까스갔다옴'); -- 여행 2일차
INSERT INTO routes
     VALUES (2, 4, 10, '여행지10갔다옴'); -- 여행 3일차
INSERT INTO routes
     VALUES (2, 6, 11, '여행지11갔다옴'); -- ROUTE_NO가 4에서 6으로 건너뛰는 경우
INSERT INTO routes
     VALUES (10000, 1, 11, '여행지11갔다옴'); -- 없는 다이어리에 루트를 추가하는 경우

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
