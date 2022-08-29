package com.routediary.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Hashtag {
  @NonNull
  public class HashTag {
    private int diaryNo;
    private String hashtag;
  }
}

