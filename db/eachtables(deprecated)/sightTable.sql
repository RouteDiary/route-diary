CREATE TABLE SIGHTS(
    SIGHT_NO NUMBER NOT NULL,
    REGION_NO NUMBER NOT NULL,
    SIGHT_NAME VARCHAR2(12 CHAR) NOT NULL,
    SIGHT_ADDR VARCHAR2(),
    SIGHT_ID NUMBER NOT NULL,
    SIGHT_CATEGORY_NAME VARCHAR2(4),
    CONSTRAINT SIGHTS_PK PRIMARY KEY(SIGHT_NO),
    CONSTRAINT SIGHTS_UK UNIQUE(SIGHT_ID),
    CONSTRAINT SIGHTS_REGIONS_FK FOREIGN KEY(REGION_NO) REFERENCES REGIONS(REGION_NO)
);

INSERT INTO SIGHTS VALUES (1, 1, '경복궁', '서울 종로구 사직로 161 경복궁', 100001, '문화시설'); -- 정상
INSERT INTO SIGHTS VALUES (2, 1, '남산', '서울 중구 회현동1가', 100001, '약국'); -- SIGHT_ID 중복
INSERT INTO SIGHTS VALUES (3, 17, '남산왕돈까스', '충북 청주시 상당구 용암북로 30 1층', 100003, '음식점'); -- 범위(1~16)를 벗어나는 REGION_ID 추가
INSERT INTO SIGHTS VALUES (4, 2, '남산왕돈까스 2호점', '충북 청주시 상당구 용암북로 30 1층', 100004, '음식점'); --  주소 중복
INSERT INTO SIGHTS VALUES (4, 2, '남산왕돈까스 서울점', '서울특별시 동작구 용암북로 30 1층', 100005, '음식점'); --  SIGHT_NO 중복
INSERT INTO SIGHTS VALUES (6, 12, '집', '경기 성남시 분당구 벌말로', 100006, '새로운카테고리'); --  카카오맵 API에 없는 SIGHT_NAME
INSERT INTO SIGHTS VALUES (7, 12, '율동공원', '성남시 분당구 문정로 72', 100007, '문화시설'); --  주소에 광역자지단체명이 없는 경우
INSERT INTO SIGHTS VALUES (8, 12, '율동공원', '경기도 광주시 초월로 70', 100008, '문화시설'); --  관광지 이름이 같은 경우
INSERT INTO SIGHTS VALUES (9, 1, '여행지9', '서울 종로구 종로', 100009, '문화시설'); -- 정상
INSERT INTO SIGHTS VALUES (10, 11, '여행지10', '서울 종로구 북로', 100010, '음식점'); -- 정상
INSERT INTO SIGHTS VALUES (11, 10, '여행지11', '서울 종로구 남로', 100011, '중개업소'); -- 카카오맵 API에 있는 SIGHT_CODE지만 관광과 상관없는 경우
