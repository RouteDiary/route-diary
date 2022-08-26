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
public class Route {
  @NonNull
  private int diaryNo;
  @NonNull
  private int routeNo;
  private String routeContent;
  private String kakaoMapId;
}
