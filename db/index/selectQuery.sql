-- 좋아요 상위 N개 뽑기
SELECT diary_no,like_cnt,diary_writting_time
FROM (SELECT *
      FROM diaries
      ORDER BY like_cnt DESC)
WHERE ROWNUM <= N;



-- 최신글 상위 N개 뽑기
SELECT diary_no
FROM(SELECT *
     FROM diaries
     ORDER BY diary_writting_time DESC
)
WHERE ROWNUM <= N;

-- N개의 게시물 보기

SELECT diary_no
     , diary_title
     , view_cnt
     , like_cnt
     , diary_writting_time
FROM diaries
WHERE ROWNUM <= N;


--m유저가 n번 여행기 좋아요 클릭시
INSERT INTO LIKES VALUES(n, m);