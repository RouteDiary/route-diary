package com.my.repository;

import com.my.dto.Route;

public interface RouteRepository {
  void insert(Route route);

  void update(Route route);

  void delete(Route route);
}
