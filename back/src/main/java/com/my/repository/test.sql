SELECT MAX(comment_no) FROM comments WHERE diary_no = 1;


INSERT INTO comments(diary_no, comment_no, client_id, comment_content, comment_writing_time)
VALUES ( 
1,  
(SELECT NVL(MAX(comment_no), 0)+1 FROM comments WHERE diary_no = 1),
'a',
'comment_test1',
SYSDATE
);
