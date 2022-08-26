package com.routediary.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestRepository {
  public Integer toSize(String mid, String subject, int doc);
}
