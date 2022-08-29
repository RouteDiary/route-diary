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
WHERE client_id = 'koreaman@naver.com'; --업데이트 할 id값
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

 
-- 다이어리 게시판에 들어갔을때 ?번째부터 ?번째 다이어리 반환 (검색어가 없을 경우) 
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
       ORDER BY ? DESC,
                diary_writing_time DESC)
WHERE rnum BETWEEN ? AND ?;

-- 다이어리 게시판에 들어갔을때 ?번째부터 ?번째 다이어리 반환 (검색어로 검색할 경우)
SELECT * 
  FROM  (SELECT d.*
              , client_nickname
              , client_status_flag
              , row_number() OVER (PARTITION BY 1 ORDER BY 1) AS rnum
           FROM diaries d
LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
LEFT OUTER JOIN routes r ON (d.diary_no = r.diary_no)
          WHERE diary_disclosure_flag = 1 AND
                diary_title LIKE '%광주%' OR
                route_content LIKE '%$광주%'
       ORDER BY diary_view_cnt DESC,
                diary_writing_time DESC)
WHERE rnum BETWEEN 1 AND 10

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
--루트 추가 SQL 
INSERT INTO ROUTES 
     VALUES (1
           , 1
           , '루트내용'
           , kakao_id);        
--루트 삭제 SQL       
DELETE FROM routes 
      WHERE diary_no = 46
        AND route_no = 4; 

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
     , diary_start_date	= TO_DATE('2022/04/12', 'yyyy/mm/dd')
     , diary_end_date = TO_DATE('2022/05/12', 'yyyy/mm/dd')
     , diary_disclosure_flag = 1
     , diary_delete_flag = 1
 WHERE diary_no = 1; 

 --다이어리 조회수 1 증가
UPDATE diaries  
   SET diary_view_cnt = ?
 WHERE diary_no = ? ;

--유저가 다이어리에 좋아요 클릭시 (americaman@gmail.com 유저가 1번 다이어리에 좋아요 눌렀을때)
INSERT INTO likes 
     VALUES (1
           , 'americaman@gmail.com');
   

--댓글 추가
INSERT INTO comments 
     VALUES (1
           , (SELECT NVL(MAX(comment_no), 0) + 1
          FROM comments WHERE diary_no = 1)
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
           
--다이어리 조회 2개 이상 SQL 이용
		   SELECT *
             FROM diaries d
  LEFT OUTER JOIN clients c ON (d.client_id = c.client_id)
            WHERE diary_no = ?;
SELECT *
  FROM routes
 WHERE diary_no = ?;
	         SELECT *
           FROM comments co
LEFT OUTER JOIN clients cl ON (co.client_id = cl.client_id)
          WHERE diary_no = ?; 