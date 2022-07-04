-- 필요한 SQL 구문 도출함

--로그인화면 SQL 구문
SELECT *
  FROM clients
 WHERE id='nkman@naver.com'
   AND pwd='asdasd'
   AND status_flag=1; --(0은 탈퇴회원이므로 로그인 실패, 1은 로그인)
   
--로그인화면 ( 관리자 ) SQL 구문
SELECT *
  FROM admins
 WHERE id=?
   AND pwd=?;

--회원가입화면 SQL 구문
INSERT INTO clients (id,pwd,client_roadaddr,client_addr,client_cellphone_no,nickname,status_flag) 
     VALUES ('taiwanman@google.com','asd','타이페이시','어딘가','010-9900-0000','대만인',1);

-- 다이어리 게시판 SQL 구문

-- 다이어리 게시판에 들어갔을때 1번째부터 10번째 다이어리 반환 (최신글 순) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.id 
              , c.nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.view_cnt
              , d.like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE diary_flag = 1 -- 공개글만 반환
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.id 
             , c.nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.view_cnt
             , d.like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE diary_flag = 1 -- 공개글만 반환
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 
       ORDER BY diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;


-- 다이어리 게시판에서 1번째부터 10번째 다이어리 반환 (좋아요수 순) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.id 
              , c.nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.view_cnt
              , d.like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE diary_flag = 1 -- 공개글만 반환
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY like_cnt DESC,
                diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.id 
             , c.nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.view_cnt
             , d.like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE diary_flag = 1 -- 공개글만 반환
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
       ORDER BY like_cnt DESC,
                diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;

-- 다이어리 게시판에서 1번째부터 10번째 다이어리 반환 (조회수 순) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.id 
              , c.nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.view_cnt
              , d.like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE diary_flag = 1 -- 공개글만 반환
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY view_cnt DESC,
                diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.id 
             , c.nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.view_cnt
             , d.like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE diary_flag = 1 -- 공개글만 반환
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
       ORDER BY view_cnt DESC,
                diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;

-- 다이어리 게시판에 들어갔을때 1번째부터 10번째 다이어리 반환 (검색어로 검색할 경우) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
SELECT DISTINCT diary_no 
              , diary_title
              , diary_writing_time
              , id
              , nickname
              , diary_start_date
              , diary_end_date
              , view_cnt
              , like_cnt
              , route_content -- 나중에 뺄것
           FROM (SELECT d.diary_no
                      , d.diary_title
                      , d.diary_writing_time
                      , d.id 
                      , c.nickname
                      , d.diary_start_date
                      , d.diary_end_date
                      , d.view_cnt
                      , d.like_cnt
                      , r.route_no -- 나중에 뺄것
                   FROM diaries d
        LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)
        LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)
        LEFT OUTER JOIN clients c ON (d.id = c.id)
                  WHERE diary_flag = 1 -- 공개글만 반환
                    AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 
                    AND (r.route_content LIKE '%경복궁%'
                         OR d.diary_title LIKE '%경복궁%'
                         OR s.sight_name LIKE '%경복궁%')
                    AND ROWNUM <= 10 -- 1~10번째 글만 반환
                    ORDER BY diary_writing_time DESC);

                    
                    
--2~20번째 (하는중)
SELECT DISTINCT diary_no 
              , diary_title
              , diary_writing_time
              , id
              , nickname
              , diary_start_date
              , diary_end_date
              , view_cnt
              , like_cnt
           FROM (SELECT diary_no 
                      , diary_title
                      , diary_writing_time
                      , id
                      , nickname
                      , diary_start_date
                      , diary_end_date
                      , view_cnt
                      , like_cnt
                      , ROWNUM r
                   FROM (SELECT d.diary_no
                              , d.diary_title
                              , d.diary_writing_time
                              , d.id 
                              , c.nickname
                              , d.diary_start_date
                              , d.diary_end_date
                              , d.view_cnt
                              , d.like_cnt
                              , r.route_content -- 나중에 뺄것
                              , r.route_no -- 나중에 뺄것
                           FROM diaries d
                LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)
                LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)
                LEFT OUTER JOIN clients c ON (d.id = c.id)
                          WHERE diary_flag = 1 -- 공개글만 반환
                            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0                           
                            AND (r.route_content LIKE '%경복궁%'
                                 OR d.diary_title LIKE '%경복궁%'
                                 OR s.sight_name LIKE '%경복궁%')
                        ORDER BY diary_writing_time DESC)
     )            
 WHERE r BETWEEN 1 AND 20;

-- 내 전체 다이어리 보기 SQL 구문

-- 내 전체 다이어리 보기 에 들어갔을때 1번째부터 10번째 다이어리 반환 : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환, 공개여부
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.id 
              , c.nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.view_cnt
              , d.like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE d.id = 'koreaman@naver.com' -- 현재 로그인 중인 계정
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0     
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.id 
             , c.nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.view_cnt
             , d.like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.id = c.id)
          WHERE d.id = 'koreaman@naver.com' -- 현재 로그인 중인 계정
            AND delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0     
       ORDER BY diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;
 
 
 --관광지 추가 SQL
INSERT INTO sights 
     VALUES (sight_no_seq.NEXTVAL
           , 1
           , '관광지 이름'
           , '관광지 주소'
           , 10001
           , '관광지 카테고리 이름'
);

--광역자치단체 정보보기 SQL
SELECT *
  FROM regions;

--루트 추가 SQL
INSERT INTO ROUTES 
     VALUES (diary_no_seq.NEXTVAL
           , 1
           , 1
           , '루트내용');

-- 다이어리 추가 SQL
INSERT INTO diaries 
     VALUES (diary_no_seq.NEXTVAL
           , 'koreaman@gmail.com'
           , '다이어리제목'
           , TO_DATE('2022/05/12', 'yyyy/mm/dd')
           , TO_DATE('2022/04/12', 'yyyy/mm/dd')
           , TO_DATE('2022/04/16', 'yyyy/mm/dd')
           , 1 --다이어리게시물 공개
           , 0 -- 조회수
           , 0 -- 좋아요수
           , 1 -- 삭제여부 (삭제안된상태 : 1, 상제된상태 : 0)
);

--유저가 다이어리에 좋아요 클릭시 (americaman@gmail.com 유저가 1번 다이어리에 좋아요 눌렀을때)
INSERT INTO likes 
     VALUES (1, 'americaman@gmail.com');

--내 계정 정보 업데이트 SQL구문
UPDATE clients
   SET pwd = '?'
     , client_roadaddr = '?'
     , client_addr = '?'
     , client_cellphone_no = '?'
     , nickname = '?'
WHERE id = 'koreaman@naver.com'; --업데이트 할 id값
SELECT * 
  FROM clients;

--회원탈퇴
UPDATE CLIENTS
SET STATUS_FLAG = '0' --0 탈퇴 ,
WHERE ID = 'koreaman@naver.com';