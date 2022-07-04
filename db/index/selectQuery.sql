-- 필요한 SQL 구문 도출함


--회원가입화면 SQL 구문
INSERT INTO clients  
     VALUES ('taiwanman@google.com','asd','010-9900-0000','대만인',1);

--내 계정 정보 업데이트 SQL구문
UPDATE clients
   SET client_pwd = '?'
     , client_cellphone_no = '?'
     , client_nickname = '?'
WHERE cleint_id = 'koreaman@naver.com'; --업데이트 할 id값
SELECT * 
  FROM clients;

--회원탈퇴
UPDATE clients
SET client_status_flag = '0' --0 탈퇴 ,
WHERE client_id = 'koreaman@naver.com';
     
--로그인화면 SQL 구문
SELECT *
  FROM clients
 WHERE client_id='nkman@naver.com'
   AND client_pwd='asdasd'
   AND client_status_flag=1; --(0은 탈퇴회원이므로 로그인 실패, 1은 로그인)
   
--로그인화면 ( 관리자 ) SQL 구문
SELECT *
  FROM admins
 WHERE admin_id=?
   AND admin_pwd=?;
   
-- 다이어리 게시판 SQL 구문
-- 다이어리 게시판에 들어갔을때 1번째부터 10번째 다이어리 반환 (최신글 순) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.client_id 
              , c.client_nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.diary_view_cnt
              , d.diary_like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.client_id 
             , c.client_nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.diary_view_cnt
             , d.diary_like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 
       ORDER BY diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;


-- 다이어리 게시판에서 1번째부터 10번째 다이어리 반환 (좋아요수 순) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.client_id 
              , c.client_nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.diary_view_cnt
              , d.diary_like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY diary_like_cnt DESC,
                diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.client_id 
             , c.client_nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.diary_view_cnt
             , d.diary_like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
       ORDER BY diary_like_cnt DESC,
                diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;

-- 다이어리 게시판에서 1번째부터 10번째 다이어리 반환 (조회수 순) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
         SELECT d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.client_id 
              , c.client_nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.diary_view_cnt
              , d.diary_like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY diary_view_cnt DESC,
                diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT  d.diary_no -- 나중에 뺄것
              , d.diary_title
              , d.diary_writing_time
              , d.client_id 
              , c.client_nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.diary_view_cnt
              , d.diary_like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0           
       ORDER BY diary_view_cnt DESC,
                diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;

-- 다이어리 게시판에 들어갔을때 1번째부터 10번째 다이어리 반환 (검색어로 검색할 경우) : 다이어리 번호, 다이어리 제목, 닉네임, 조회수, 좋아요수, 여행시작일자, 종료일자 반환
SELECT DISTINCT diary_no 
              , diary_title
              , diary_writing_time
              , client_id 
              , client_nickname
              , diary_start_date
              , diary_end_date
              , diary_view_cnt
              , diary_like_cnt
              , route_content -- 나중에 뺄것
           FROM (SELECT d.diary_no
                      , d.diary_title
                      , d.diary_writing_time
                      , d.client_id 
                      , c.client_nickname
                      , d.diary_start_date
                      , d.diary_end_date
                      , d.diary_view_cnt
                      , d.diary_like_cnt
                      , r.route_no -- 나중에 뺄것
                   FROM diaries d
        LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)
        LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)
        LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
                  WHERE diary_disclosure_flag = 1 -- 공개글만 반환
                    AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0 
                    AND (r.route_content LIKE '%경복궁%'
                         OR d.diary_title LIKE '%경복궁%'
                         OR s.sight_name LIKE '%경복궁%')
                    AND ROWNUM <= 10 -- 1~10번째 글만 반환
                    ORDER BY diary_writing_time DESC);

                    
                    
--2~20번째
SELECT DISTINCT diary_no 
              , diary_title
              , diary_writing_time
              , client_id
              , client_nickname
              , diary_start_date
              , diary_end_date
              , diary_view_cnt
              , diary_like_cnt
           FROM (SELECT diary_no 
                      , diary_title
                      , diary_writing_time
                      , client_id
                      , client_nickname
                      , diary_start_date
                      , diary_end_date
                      , diary_view_cnt
                      , diary_like_cnt
                      , ROWNUM r
                   FROM (SELECT d.diary_no
                              , d.diary_title
                              , d.diary_writing_time
                              , d.client_id 
                              , c.client_nickname
                              , d.diary_start_date
                              , d.diary_end_date
                              , d.diary_view_cnt
                              , d.diary_like_cnt
                              , r.route_content -- 나중에 뺄것
                              , r.route_no -- 나중에 뺄것
                           FROM diaries d
                LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)
                LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)
                LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
                          WHERE diary_disclosure_flag = 1 -- 공개글만 반환
                            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0                           
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
              , d.client_id 
              , c.client_nickname
              , d.diary_start_date
              , d.diary_end_date
              , d.diary_view_cnt
              , d.diary_like_cnt
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE d.client_id = 'koreaman@naver.com' -- 현재 로그인 중인 계정
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0     
            AND ROWNUM <= 10 -- 1~10번째 글만 반환
       ORDER BY diary_writing_time DESC;
-- 11~20번째 반환
SELECT * 
  FROM (SELECT d.diary_no -- 나중에 뺄것
             , d.diary_title
             , d.diary_writing_time
             , d.client_id 
             , c.client_nickname
             , d.diary_start_date
             , d.diary_end_date
             , d.diary_view_cnt
             , d.diary_like_cnt
             , ROWNUM r
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE d.client_id = 'koreaman@naver.com' -- 현재 로그인 중인 계정
            AND diary_delete_flag = 1 -- 삭제안된 상태 : 1 / 상제된 상태 : 0     
       ORDER BY diary_writing_time DESC)
 WHERE r BETWEEN 11 AND 20;
 
-- 게시글 작성 
 --관광지 추가 SQL
INSERT INTO sights 
     VALUES (sight_no_seq.NEXTVAL
           , '관광지 이름'
           , '관광지 주소'
           , 10001
           , '관광지 카테고리 이름'
);

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
           , TO_DATE(SYSDATE, 'yyyy/mm/dd')
           , TO_DATE('2022/04/12', 'yyyy/mm/dd')
           , TO_DATE('2022/04/16', 'yyyy/mm/dd')
           , 1 --다이어리게시물 공개
           , 0 -- 조회수
           , 0 -- 좋아요수
           , 1 -- 삭제여부 (삭제안된상태 : 1, 상제된상태 : 0)
);

--유저가 다이어리에 좋아요 클릭시 (americaman@gmail.com 유저가 1번 다이어리에 좋아요 눌렀을때)
INSERT INTO likes 
     VALUES (1
           , 'americaman@gmail.com');

-- 유저가 다이어리에 좋아요 클릭시 diaries table의 diary_like_cnt 증가/감소 트리거
-- 구현해야함     

--댓글 추가
INSERT INTO comments 
     VALUES (1
           , 1
           , 'koreaman@gmail.com'
           , '댓글내용'
           , TO_DATE(SYSDATE, 'yyyy/mm/dd'));          
--댓글 수정
UPDATE comments  
   SET comment_content = '수정된 내용'
     , comment_writing_time = TO_DATE(SYSDATE, 'yyyy/mm/dd')
 WHERE client_id='koreaman@gmail.com';
--댓글 삭제
DELETE FROM comments 
      WHERE client_id='koreaman@gmail.com';
           
--다이어리 조회 (미완성)
		   SELECT d.diary_title
			    , 다이어리작성자닉네임
			    , d.diary_writing_time
			    , d.diary_start_date
			    , d.diary_end_date
			    , d.diary_view_cnt
			    , d.diary_like_cnt
			    , route_content
			    , sight_name
			    , 댓글작성자닉네임
			    , comment_content
			    , comment_writing_time
             FROM diaries d;
  LEFT OUTER JOIN clients c ON (d.client_id = c.client_id);