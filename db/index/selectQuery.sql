-- 필요한 SQL 구문 도출함


--회원가입화면 SQL 구문
INSERT INTO clients  
     VALUES ('taiwanman@google.com','asd','010-9900-0000','대만인',1);

--내 계정 정보 업데이트 OR 회원탈퇴 SQL구문 
UPDATE clients
   SET client_pwd = '?'
     , client_cellphone_no = '?'
     , client_nickname = '?'
     , client_status_flag = '0'
WHERE cleint_id = 'koreaman@naver.com'; --업데이트 할 id값
SELECT * 
  FROM clients;

     
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
-- * diary_delete_flag = 삭제안된 상태 : 1 / 삭제된 상태 : 0
-- 다이어리 게시판에 들어갔을때 ?번째부터 ?번째 다이어리 반환 (최신글 순)
-- Return diaries, clients' columns 
SELECT * 
  FROM  (SELECT d.*
              , client_pwd
              , client_cellphone_no
              , client_nickname
              , client_status_flag
              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1
            AND diary_delete_flag = 1
       ORDER BY diary_writing_time DESC)
WHERE rnum BETWEEN ? AND ?;

-- 다이어리 게시판에서 ?번째부터 ?번째 다이어리 반환 (조회수 순)
SELECT * 
  FROM  (SELECT d.*
              , client_pwd
              , client_cellphone_no
              , client_nickname
              , client_status_flag
              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1
            AND diary_delete_flag = 1
       ORDER BY diary_view_cnt DESC,
                diary_writing_time DESC)
WHERE rnum BETWEEN ? AND ?;
 
-- 다이어리 게시판에서 ?번째부터 ?번째 다이어리 반환 (좋아요수 순) 
SELECT * 
  FROM  (SELECT d.*
              , client_pwd
              , client_cellphone_no
              , client_nickname
              , client_status_flag
              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE diary_disclosure_flag = 1
            AND diary_delete_flag = 1
       ORDER BY diary_like_cnt DESC,
                diary_writing_time DESC)
WHERE rnum BETWEEN ? AND ?;

-- 다이어리 게시판에 들어갔을때 ?번째부터 ?번째 다이어리 반환 (검색어로 검색할 경우)
SELECT DISTINCT diary_no
              , client_id 
              , diary_title
              , diary_writing_time
              , diary_start_date
              , diary_end_date
              , diary_disclosure_flag
              , diary_view_cnt
              , diary_like_cnt
              , diary_delete_flag
              , client_pwd
              , client_cellphone_no
              , client_nickname
              , client_status_flag 
              , rnum
           FROM (SELECT diary_no 
                      , client_id 
                      , diary_title
                      , diary_writing_time
                      , diary_start_date
                      , diary_end_date
                      , diary_disclosure_flag
                      , diary_view_cnt
                      , diary_like_cnt
                      , diary_delete_flag
                      , client_pwd
                      , client_cellphone_no
                      , client_nickname
                      , client_status_flag
                      , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum
                   FROM (SELECT d.diary_no
                              , d.client_id 
                              , d.diary_title
                              , d.diary_writing_time
                              , d.diary_start_date
                              , d.diary_end_date
                              , d.diary_disclosure_flag
                              , d.diary_view_cnt
                              , d.diary_like_cnt
                              , d.diary_delete_flag
                              , c.client_pwd
                              , c.client_cellphone_no
                              , c.client_nickname
                              , c.client_status_flag
                           FROM diaries d
                LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)
                LEFT OUTER JOIN sights s ON (r.sight_no = s.sight_no)
                LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
                          WHERE diary_disclosure_flag = 1
                            AND diary_delete_flag = 1                        
                            AND (r.route_content LIKE '%경복궁%'
                                 OR d.diary_title LIKE '%경복궁%'
                                 OR s.sight_name LIKE '%경복궁%')
                        ORDER BY diary_writing_time DESC)
     )            
 WHERE rnum BETWEEN ? AND ?;

-- 내 전체 다이어리 보기 SQL 구문

-- 내 전체 다이어리 보기 에 들어갔을때 ?번째부터 ?번째 다이어리 반환
SELECT * 
  FROM (SELECT d.*
             , client_pwd
             , client_cellphone_no
             , client_nickname
             , client_status_flag
             , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum
          FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
          WHERE d.client_id = 'a' 
            AND diary_delete_flag = 1 
       ORDER BY diary_writing_time DESC)
 WHERE rnum BETWEEN ? AND ?;
 
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
--루트 수정 SQL 
UPDATE routes 
   SET sight_no = 1
     , route_content = '새로운내용' 
 WHERE route_no = 1;          
--루트 삭제 SQL       
DELETE FROM routes 
      WHERE diary_no = 1; 

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
--다이어리 수정 & 삭제 SQL
UPDATE diaries 
   SET diary_title = '새로운제목'
     , diary_writing_time = TO_DATE(SYSDATE, 'yyyy/mm/dd')
     , diary_start_date	= TO_DATE('2022/04/12', 'yyyy/mm/dd')
     , diary_end_date = TO_DATE('2022/05/12', 'yyyy/mm/dd')
     , diary_disclosure_flag = 1
     , diary_delete_flag = 1
 WHERE diary_no = 1; 


--유저가 다이어리에 좋아요 클릭시 (americaman@gmail.com 유저가 1번 다이어리에 좋아요 눌렀을때)
INSERT INTO likes 
     VALUES (1
           , 'americaman@gmail.com');
   

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
           
--다이어리 조회 4개 SQL 이용
		   SELECT d.diary_title
			    , c.client_nickname
			    , d.diary_writing_time
			    , d.diary_start_date
			    , d.diary_end_date
			    , d.diary_view_cnt
			    , d.diary_like_cnt
             FROM diaries d;
  LEFT OUTER JOIN clients c ON (d.client_id = c.client_id);
SELECT *
  FROM routes;
 WHERE diary_no = 1;
SELECT *
  FROM comments;
 WHERE diary_no = 1; 
SELECT *
  FROM sights;
 WHERE sight_no = (SELECT sight_no
 					 FROM routes
 					WHERE routes_no = 1); 
 					
 