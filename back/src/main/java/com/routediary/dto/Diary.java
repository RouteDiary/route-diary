<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> ceecc3b (Feat: Notice.xml 메서드 통합)
package com.routediary.dto;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
  private int diaryNo;
  private String diaryTitle;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryWritingTime;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryModifyingTime;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  private Date diaryStartDate;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  private Date diaryEndDate;
  private Integer diaryDisclosureFlag;
  private int diaryViewCnt;
  private int diaryLikeCnt;
  private Client client;
  private List<Route> routes;
  private List<Comment> comments;
<<<<<<< HEAD
  private List<Hashtag> hashtags;
}
=======
package com.routediary.dto;


import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
  private int diaryNo;
  private String diaryTitle;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryWritingTime;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date diaryModifyingTime;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  private Date diaryStartDate;
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
  private Date diaryEndDate;
  private Integer diaryDisclosureFlag;
  private int diaryViewCnt;
  private int diaryLikeCnt;
  private Client client;
  private List<Route> routes;
  private List<Comment> comments;
  private List<Hashtag> hashtags;
}
>>>>>>> 58ded0b (Feat: ClientRepository 완성 & test 완료)
=======
  private List<HashTag> hashtags;
}
>>>>>>> ceecc3b (Feat: Notice.xml 메서드 통합)
