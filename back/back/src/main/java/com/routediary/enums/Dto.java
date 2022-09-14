package com.routediary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dto {
  ADMIN("admin"), CLIENT("client"), COMMENT("comment"), DIARY("diary"), HASHTAG("hashtag"), LIKE(
      "like"), NOTICE("notice"), ROUTE("route"), PAGEBEAN("pagebean"), RESULTBEAN("resultbean");

  private final String name;
}
