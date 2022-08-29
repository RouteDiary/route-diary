<<<<<<< HEAD
<<<<<<< HEAD
package com.routediary.dto;

import java.util.Date;
=======
package com.routediary.dto;

import java.sql.Date;
>>>>>>> ceecc3b (Feat: Notice.xml 메서드 통합)
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======
import lombok.NonNull;
>>>>>>> ceecc3b (Feat: Notice.xml 메서드 통합)
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
  private int diaryNo;
  private int commentNo;
  private String commentContent;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date commentWritingTime;
<<<<<<< HEAD
  private Client client;
}
=======
package com.routediary.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
  private int diaryNo;
  private int commentNo;
  private String commentContent;
  @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "Asia/Seoul")
  private Date commentWritingTime;
  private Client client;
}
>>>>>>> 58ded0b (Feat: ClientRepository 완성 & test 완료)
=======
  @NonNull
  private Client client;
}
>>>>>>> ceecc3b (Feat: Notice.xml 메서드 통합)
