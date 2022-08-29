<<<<<<< HEAD
package com.routediary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  @NonNull
  private String clientId;
  private String clientPwd;
  private String clientCellphoneNo;
  private String clientNickname;
  private int clientStatusFlag;

}


=======
package com.routediary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  @NonNull
  private String clientId;
  private String clientPwd;
  private String clientCellphoneNo;
  private String clientNickname;
  private Integer clientStatusFlag;

}


>>>>>>> 58ded0b (Feat: ClientRepository 완성 & test 완료)
