create trigger like_cnt_trig
after insert or delete on likes
for each row
begin
if inserting then
    update diaries set diary_like_cnt = (nvl(diary_like_cnt,0) + 1);
elsif deleting then 
    update diaries set diary_like_cnt = (nvl(diary_like_cnt,0) - 1);
end if;
end;