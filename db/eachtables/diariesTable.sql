CREATE TABLE DIARIES (
    DIARY_NO NUMBER(6) NOT NULL, -- PK
    ID VARCHAR2(20 CHAR) NOT NULL, -- FK 20자리까지 가능
    DIARY_TITLE VARCHAR2(30 CHAR) NOT NULL,  -- 한글로 30자 가능
    DIARY_WRITING_TIME DATE NOT NULL,
    DIARY_START_DATE DATE NOT NULL,
    DIARY_END_DATE DATE NOT NULL,
    DIARY_FLAG NUMBER(1) NOT NULL, -- 1이 보이기 0은 숨기기
    VIEW_CNT NUMBER(5) NOT NULL,
    LIKE_CNT NUMBER(5) NOT NULL,
    CONSTRAINT DIARIES_PK PRIMARY KEY(DIARY_NO),
    CONSTRAINT DIARIES_CLIENTS_FK FOREIGN KEY(ID) REFERENCES CLIENTS(ID)
);

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'koreaman@gmail.com', '즐거운 서울로 떠나요',
                            TO_DATE('20220622 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200710','yyyy-mm-dd'), TO_DATE('20200712','yyyy-mm-dd'),
                            1, 1523,  0);
INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'japanman@gmail.com', '즐거운 부산으로 떠나요',
                            TO_DATE('20220623 102311','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200711','yyyy-mm-dd'), TO_DATE('20200714','yyyy-mm-dd'),
                            0, 1525,  1);

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'chinaman@gmail.com', '즐거운 대구로 떠나요',
                            TO_DATE('20220624 102411','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200715','yyyy-mm-dd'), TO_DATE('20200719','yyyy-mm-dd'),
                            1, 15235,  4); -- 공개 여부 중복시

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'americaman@gmail.com', '즐거운 광주으로 떠나요',
                            TO_DATE('20220625 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200801','yyyy-mm-dd'), TO_DATE('20200820','yyyy-mm-dd'),
                            1, 15231, 5); 

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'koreawoman@gmail.com', '즐거운 인천으로 떠나요',
                            TO_DATE('20220626 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200101','yyyy-mm-dd'), TO_DATE('20200320','yyyy-mm-dd'),
                            0, 15231,  6);

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'japanwoman@gmail.com', '즐거운 대전으로 떠나요',
                            TO_DATE('20220626 202011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200201','yyyy-mm-dd'), TO_DATE('20200220','yyyy-mm-dd'),
                            1, 15231, 0);

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'chinawoman@gmail.com', '즐거운 울산으로 떠나요',
                            TO_DATE('20220627 222011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200301','yyyy-mm-dd'), TO_DATE('20200420','yyyy-mm-dd'),
                            1, 15231, 1);

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'americawoman@gmail.com', '즐거운 세종으로 떠나요',
                            TO_DATE('20220629 102011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200401','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            0, 15231, 2);

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'americawoman@gmail.com', '즐거운 경남으로 떠나요',
                            TO_DATE('20220629 112011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200402','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            1, 15251, 9,3); -- 아이디 중복 인 경우

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'americawoman@naver.com', '즐거운 경남으로 떠나요',
                            TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200403','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            0, 1521, 5); -- 제목 중복 인 경우

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'americaman@daum.net', '즐거운 경북으로 떠나요',
                            TO_DATE('20220629 152011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            1, 1522,  6);  

INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'koreaman@naver.com', '즐거운 전남으로 떠나요',
                            TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            0, 1231, 7); -- 여행기간이 같을 경우
                        
INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'koreaman@naver.com', '즐거운 전북으로 떠나요',
                            TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            0, 1231, 8); -- 뷰 수가 같을 경우
INSERT INTO DIARIES VALUES(DIARY_NO_SEQ.NEXTVAL, 'man@naver.com', '즐거운 전북으로 떠나요',
                            TO_DATE('20220629 132011','yyyy-mm-dd hh24:mi:ss'), TO_DATE('20200404','yyyy-mm-dd'), TO_DATE('20200520','yyyy-mm-dd'),
                            0, 1231, 9); -- id가 client 테이블에 없을 경우 (에러 예상)
