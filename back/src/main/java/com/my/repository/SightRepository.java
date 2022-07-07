package com.my.repository;

import com.my.dto.Sight;
import com.my.exception.InsertException;

public interface SightRepository {
  void insert(Sight sight) throws InsertException;

}
