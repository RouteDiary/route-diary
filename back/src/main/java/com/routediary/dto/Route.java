package com.routediary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {
  private int diaryNo;
  private int routeNo;
  private String routeContent;
  private String kakaoMapId;
}
