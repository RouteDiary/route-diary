package com.my.repository;

import com.my.dto.Route;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.UpdateException;

public interface RouteRepository {
  void insert(Route route) throws InsertException;

  void update(Route route) throws UpdateException;

  void delete(Route route) throws DeleteException;
}
