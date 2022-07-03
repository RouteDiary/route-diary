CREATE TABLE CLIENTS (
    ID VARCHAR2(20 CHAR) NOT NULL,
    PWD VARCHAR2(20 CHAR) NOT NULL,
    CLIENT_ROADADDR VARCHAR2(20 CHAR) NOT NULL,
    CLIENT_ADDR VARCHAR2(20 CHAR) NOT NULL,
    CLIENT_CELLPHONE_NO VARCHAR2(15) NOT NULL,
    NICKNAME VARCHAR2(10 CHAR) NOT NULL,
    CONSTRAINT CLIENTS_PK PRIMARY KEY(ID)
);

INSERT INTO CLIENTS VALUES ('koreaman@gmail.com', 'asd11','성남시 어딘가', '830호', '010-1111-2234','한국남자1');
INSERT INTO CLIENTS VALUES ('japanman@gmail.com', 'asd12','성남시 어딘가', '831호', '010-1112-2234','도로 주소 중복'); -- 도로 주소 중복
INSERT INTO CLIENTS VALUES ('chinaman@gmail.com', 'asd13','성남시 어딘가1', '832호', '010-1113-2234','한국남자3');
INSERT INTO CLIENTS VALUES ('americaman@gmail.com', 'asd14','성남시 어딘가2', '833호', '010-1114-2234','한국남자4');
INSERT INTO CLIENTS VALUES ('koreawoman@gmail.com', 'asd15','성남시 어딘가3', '834호', '010-1115-2234','한국남자5');
INSERT INTO CLIENTS VALUES ('japanwoman@gmail.com', 'asd16','성남시 어딘가4', '835호', '010-1116-2234','한국남자6');
INSERT INTO CLIENTS VALUES ('chinawoman@gmail.com', 'asd17','성남시 어딘가5', '836호', '010-1117-2234','한국남자7');
INSERT INTO CLIENTS VALUES ('americawoman@gmail.com', 'asd18','성남시 어딘가6', '837호', '010-1118-2234','한국남자8');
INSERT INTO CLIENTS VALUES ('americawoman@naver.com', 'asd19','성남시 어딘가7', '838호', '010-1119-2234','한국남자9');
INSERT INTO CLIENTS VALUES ('americaman@daum.net', 'asd11','성남시 어딘가8', '839호', '010-1110-2234','비번 중복'); -- 비번 중복
INSERT INTO CLIENTS VALUES ('koreaman@naver.com', 'asd20','성남시 어딘가9', '840호', '010-1111-2234','전화 번호 중복'); -- 전화 번호 중복
INSERT INTO CLIENTS VALUES ('koreawoman@naver.com', 'asd21','성남시 어딘가0', '830호', '010-1121-2235','상세 주소 중복'); -- 상세 주소 중복
INSERT INTO CLIENTS VALUES ('koreawoman@daum.net', 'asd22','성남시 어딘가1', '841호', '010-1121-2236','아이디와 이메일 주소가 다름'); -- 아이디와 이메일 주소 중복
INSERT INTO CLIENTS VALUES ('japanwoman@naver.com', 'asd23','성남시 어딘가2', '842호', '010-1121-2237','한국남자1'); -- 닉네임 중복
INSERT INTO CLIENTS VALUES ('japanwoman@naver.com', 'asd23','성남시 어딘가2', '842호', '010-1121-2237','한국남자1'); -- 아이디 중복 (에러 예상)

