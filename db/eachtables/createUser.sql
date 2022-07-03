CREATE USER admin_travel -- USER_ID 
IDENTIFIED BY travel; -- USER_PWD

GRANT CONNECT, RESOURCE TO admin_travel; -- CONNECT create session 권한이 있음
                                         -- RESOURCE create 트리거, 시퀀스, 타입, 프로시저, 테이블 등 8가지 권한이 부여되어있음