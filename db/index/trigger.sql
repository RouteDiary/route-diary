create or replace trigger like_cnt_trig
after insert or delete on likes
for each row
begin
if inserting then
    update diaries set diary_like_cnt = (nvl(diary_like_cnt,0) + 1) where :new.diary_no = diaries.diary_no;
elsif deleting then 
    update diaries set diary_like_cnt = (nvl(diary_like_cnt,0) - 1) where :old.diary_no = diaries.diary_no;
end if;
end;
