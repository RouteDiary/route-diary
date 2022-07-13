package com.my.repository;

import java.util.List;
import com.my.dto.Route;
import com.my.exception.DeleteException;
import com.my.exception.InsertException;
import com.my.exception.SelectException;

public interface RouteRepository {
  int selectRoutesRowSizeBydiaryNo(int diaryNo) throws SelectException;

  void insert(List<Route> routes) throws InsertException;

  void delete(List<Route> routes) throws DeleteException;
}
