package com.routediary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Hashtag;
import com.routediary.dto.Like;
import com.routediary.dto.PageBean;
import com.routediary.dto.Route;
import com.routediary.enums.Dto;
import com.routediary.enums.ErrorCode;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.exception.InvalidActionException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NumberNotFoundException;
import com.routediary.exception.RemoveException;
import com.routediary.functions.ServiceFunctions;
import com.routediary.repository.CommentRepository;
import com.routediary.repository.DiaryRepository;
import com.routediary.repository.HashtagRepository;
import com.routediary.repository.LikeRepository;
import com.routediary.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "DiaryService")
public class DiaryServiceImpl implements DiaryService {

  @Autowired
  private ServiceFunctions serviceFunctions;
  @Autowired
  private DiaryRepository diaryRepository;
  @Autowired
  private RouteRepository routeRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private LikeRepository likeRepository;
  @Autowired
  private HashtagRepository hashtagRepository;
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void writeDiary(Diary diary, List<MultipartFile> imageFiles) throws AddException {
    // Routes, Hashtags를 batch를 이용하여 insert함
    List<Route> routes = diary.getRoutes();
    List<Hashtag> hashtags = diary.getHashtags();
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    int diaryNo = 0;
    try {
      diaryRepository.insert(diary);
      diaryNo = diaryRepository.selectLatestDiaryNo(); // 저장된 다이어리의 diaryNo 구하기
      routeRepository = sqlSession.getMapper(RouteRepository.class);
      for (Route route : routes) {
        route.setDiaryNo(diaryNo);
        routeRepository.insert(route);
        sqlSession.commit();
      }
      hashtagRepository = sqlSession.getMapper(HashtagRepository.class);
      for (Hashtag hashtag : hashtags) {
        hashtag.setDiaryNo(diaryNo);
        hashtagRepository.insert(hashtag);
        sqlSession.commit();
      }
      serviceFunctions.saveImages(diaryNo, Dto.DIARY, imageFiles, true);

    } catch (Exception e) {
      e.printStackTrace();
      serviceFunctions.removeOriginalFiles(diaryNo, Dto.DIARY);
      sqlSession.rollback();
      throw new AddException();
    } finally {
      sqlSession.close();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void modifyDiary(Diary diary, List<MultipartFile> imageFiles) throws ModifyException {
    // Routes, Hashtags를 batch를 이용하여 insert함
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    List<Route> routes = diary.getRoutes();
    List<Hashtag> hashtags = diary.getHashtags();
    int diaryNo = diary.getDiaryNo();
    try {
      serviceFunctions.transferFilesToTempFolder(diaryNo, Dto.DIARY);
      routeRepository.deleteAll(diaryNo);
      hashtagRepository.deleteAll(diaryNo);
      sqlSession.commit();
      routeRepository = sqlSession.getMapper(RouteRepository.class);
      for (Route route : routes) {
        routeRepository.insert(route);
        sqlSession.commit();
      }
      hashtagRepository = sqlSession.getMapper(HashtagRepository.class);
      for (Hashtag hashtag : hashtags) {
        hashtagRepository.insert(hashtag);
        sqlSession.commit();
      }
      diaryRepository.update(diary);
      serviceFunctions.saveImages(diaryNo, Dto.DIARY, imageFiles, true);
      serviceFunctions.removeTempFiles(diaryNo, Dto.DIARY);
    } catch (Exception e) {
      e.printStackTrace();
      serviceFunctions.transferFilesToOriginalFolder(diaryNo, Dto.DIARY);
      sqlSession.rollback();
      throw new ModifyException();
    } finally {
      sqlSession.close();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeDiary(int diaryNo) throws RemoveException {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    try {
      serviceFunctions.transferFilesToTempFolder(diaryNo, Dto.DIARY);
      hashtagRepository.deleteAll(diaryNo);
      routeRepository.deleteAll(diaryNo);
      likeRepository.deleteAll(diaryNo);
      commentRepository.deleteAll(diaryNo);
      sqlSession.commit();
      diaryRepository.delete(diaryNo);
      serviceFunctions.removeTempFiles(diaryNo, Dto.DIARY);
    } catch (Exception e) {
      e.printStackTrace();
      serviceFunctions.transferFilesToOriginalFolder(diaryNo, Dto.DIARY);
      sqlSession.rollback();
      throw new RemoveException();
    }
  }

  @Override
  public void writeComment(Comment comment) throws AddException {
    commentRepository.insert(comment);
  }

  @Override
  public void modifyComment(Comment comment) throws ModifyException {
    commentRepository.update(comment);
  }

  @Override
  public void removeComment(int diaryNo, int commentNo) throws RemoveException {
    commentRepository.delete(diaryNo, commentNo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void clickLikeToggle(boolean isLike, Like like)
      throws AddException, RemoveException, InvalidActionException {
    int diaryNo = like.getDiaryNo();
    String clientId = like.getClientId();
    if (isLike == true) {
      try {
        likeRepository.insert(like);
        diaryRepository.updateIncreaseLikeCnt(diaryNo);
      } catch (Exception e) {
        throw new AddException();
      }
    } else {
      try {
        Like likeInDb = likeRepository.selectLike(diaryNo, clientId);
        if (likeInDb == null) {
          throw new InvalidActionException(ErrorCode.LIKE_CANCLE_FAILURE);
        }
        likeRepository.delete(like);
        diaryRepository.updateDecreaseLikeCnt(diaryNo);
      } catch (Exception e) {
        throw new RemoveException();
      }
    }
  }

  @Override
  public PageBean<Diary> showDiaryBoard(int order, int currentPage, List<String> hashtags)
      throws FindException, NumberNotFoundException {
    List<Diary> diaries = null;
    if (order >= 4 || order <= 0) {
      throw new NumberNotFoundException(ErrorCode.INVALID_ORDER);
    }
    int totalRows = diaryRepository.selectCountByDisclosureFlag(1);
    int[] rowArr = serviceFunctions.calculateStartAndEndRow(currentPage, totalRows);
    int startRow = rowArr[0];
    int endRow = rowArr[1];
    if (hashtags == null) {
      diaries = diaryRepository.selectDiaries(order, startRow, endRow, null);
    } else {
      diaries = diaryRepository.selectDiaries(order, startRow, endRow, hashtags);
    }
    PageBean<Diary> pageBean = (PageBean<Diary>) serviceFunctions.calculatePageBean(currentPage,
        totalRows, (List<?>) diaries);
    return pageBean;
  }

  @Override
  public PageBean<Diary> showMyDiaryBoard(int currentPage, String clientId) throws FindException {
    List<Diary> diaries = null;
    int totalRows = diaryRepository.selectCountByClientId(clientId);
    int[] rowArr = serviceFunctions.calculateStartAndEndRow(currentPage, totalRows);
    int startRow = rowArr[0];
    int endRow = rowArr[1];
    diaries = diaryRepository.selectDiariesByClientId(clientId, startRow, endRow);
    PageBean<Diary> pageBean = (PageBean<Diary>) serviceFunctions.calculatePageBean(currentPage,
        totalRows, (List<?>) diaries);
    return pageBean;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Diary showDiary(int diaryNo) throws FindException, NumberNotFoundException {
    // 다이어리의 조회수 1 증가
    diaryRepository.updateViewCnt(diaryNo);
    Diary diary = diaryRepository.selectDiary(diaryNo);
    if (diary == null) {
      throw new NumberNotFoundException(ErrorCode.POST_NOT_FOUND);
    }
    return diary;
  }

  @Override
  public Map<String, List<Diary>> showIndexPage() throws FindException {
    Map<String, List<Diary>> map = new HashMap<String, List<Diary>>();
    List<Diary> top5DiariesByLikeCnt = diaryRepository.selectDiaries(3, 1, 5, null);
    map.put("top5DiariesByLikeCnt", top5DiariesByLikeCnt);
    List<Diary> top5DiariesByWritingTime = diaryRepository.selectDiaries(2, 1, 5, null);
    map.put("top5DiariesByWritingTime", top5DiariesByWritingTime);
    return map;
  }
}
