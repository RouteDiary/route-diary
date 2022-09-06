package com.routediary.control;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.routediary.dto.Client;
import com.routediary.dto.Comment;
import com.routediary.dto.Diary;
import com.routediary.dto.Like;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;
import com.routediary.exception.AddException;
import com.routediary.exception.DiaryException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.service.DiaryService;

@RestController
@RequestMapping
public class DiaryController {
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private DiaryService diaryService;

  @PostMapping(value = "diary/write")
  public ResultBean<?> writeDiary(@RequestPart List<MultipartFile> imageFiles,
      @RequestPart Diary diary) throws DiaryException {
    ResultBean<?> resultBean = new ResultBean<>();
    try {
      diaryService.writeDiary(diary, imageFiles);
      resultBean.setStatus(1);
      resultBean.setMessage("다이어리 작성에 성공하였습니다.");
    } catch (Exception e) {
      resultBean.setStatus(0);
      resultBean.setMessage("다이어리 작성에 실패하였습니다. \n" + e.getMessage());
    }
    return resultBean;
  }

  @PutMapping(value = "diary/{diaryNo}")
  public ResultBean<?> modifyDiary(@PathVariable int diaryNo,
      @RequestPart List<MultipartFile> imageFiles, @RequestPart Diary diary) throws DiaryException {
    ResultBean<?> resultBean = new ResultBean<>();
    try {
      diaryService.modifyDiary(diary, imageFiles);
      resultBean.setStatus(1);
      resultBean.setMessage(diaryNo + "번다이어리 수정에 성공하였습니다.");
    } catch (Exception e) {
      resultBean.setStatus(0);
      resultBean.setMessage(diaryNo + "번다이어리 수정에 실패하였습니다. \n" + e.getMessage());
    }
    return resultBean;
  }

  @DeleteMapping(value = "diary/{diaryNo}")
  public ResultBean<?> removeDiary(@PathVariable int diaryNo) {
    ResultBean<?> resultBean = new ResultBean();
    try {
      diaryService.removeDiary(diaryNo);
      resultBean.setStatus(1);
      resultBean.setMessage(diaryNo + "번 다이어리 삭제에 성공하였습니다.");
    } catch (RemoveException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage(diaryNo + "번 다이어리 삭제에 실패하였습니다.");
    }
    return resultBean;
  }

  @GetMapping(value = {"diary/mylist", "diary/mylist/{pageNo}"})
  public ResultBean<PageBean<Diary>> showMyDiaryBoard(@PathVariable Optional<Integer> pageNo,
      HttpSession session) throws DiaryException {
    ResultBean<PageBean<Diary>> resultBean = new ResultBean<PageBean<Diary>>();
    int currentPageNo;
    if (pageNo.isPresent()) {
      currentPageNo = pageNo.get();
    } else {
      currentPageNo = 1;
    }
    try {
      String loginedId = (String) session.getAttribute("loginInfo");
      PageBean<Diary> pageBean = diaryService.showMyDiaryBoard(currentPageNo, loginedId);
      resultBean.setStatus(1);
      resultBean.setT(pageBean);
      resultBean.setMessage(pageBean.getCurrentPage() + "페이지 불러오기 성공");
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage(pageNo + "페이지를 불러오는데 실패하였습니다.");
    }
    return resultBean;
  }

  @GetMapping(value = "diary/{diaryNo}")
  public ResultBean<Diary> showDiary(@PathVariable int diaryNo) {
    ResultBean<Diary> resultBean = new ResultBean<>();
    try {
      Diary diary = diaryService.showDiary(diaryNo);
      resultBean.setStatus(1);
      resultBean.setT(diary);
      resultBean.setMessage(diaryNo + "번 다이어리를 불러오는데 성공하였습니다.");
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage(diaryNo + "번 다이어리를 불러오는데 실패하였습니다.");
    }
    return resultBean;
  }

  @GetMapping(value = {"diary/list/{order}", "diary/list/{order}/{pageNo}"})
  public ResultBean<PageBean<Diary>> showDiaryBoard(@PathVariable Optional<Integer> pageNo,
      @PathVariable int order, @RequestBody List<String> hashtags) {
    ResultBean<PageBean<Diary>> resultBean = new ResultBean<PageBean<Diary>>();
    int currentPageNo;
    if (pageNo.isPresent()) {
      currentPageNo = pageNo.get();
    } else {
      currentPageNo = 1;
    }
    try {
      PageBean<Diary> pageBean = diaryService.showDiaryBoard(order, currentPageNo, hashtags);
      resultBean.setStatus(1);
      resultBean.setT(pageBean);
      resultBean.setMessage(pageNo + "번 페이지를 불러오는데 성공하였습니다.");
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage(pageNo + "번 페이지를 불러오는데 실패하였습니다.");
    }
    return resultBean;
  }

  @PostMapping(value = "like/{isLike}")
  public ResultBean<?> clikeLikeToggle(@PathVariable boolean isLike, @RequestBody Like like) {
    ResultBean<?> resultBean = new ResultBean();
    try {
      diaryService.clickLikeToggle(isLike, like);
      resultBean.setStatus(1);
      resultBean.setMessage("좋아요 증가/감소에 성공하였습니다.");
    } catch (AddException | RemoveException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage("좋아요 증가/감소에 실패하였습니다.");
    }
    return resultBean;
  }

  @GetMapping(value = "/")
  public ResultBean<Map<String, List<Diary>>> showIndexPage() {
    ResultBean<Map<String, List<Diary>>> resultBean = new ResultBean<Map<String, List<Diary>>>();
    try {
      Map<String, List<Diary>> diaries = diaryService.showIndexPage();
      resultBean.setStatus(1);
      resultBean.setT(diaries);
      resultBean.setMessage("다이어리를 불러오는데 성공하였습니다.");
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage("다이어리를 불러오는데 실패하였습니다.");
    }
    return resultBean;
  }

  @PostMapping(value = "diary/{diaryNo}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> writeComment(@PathVariable int diaryNo, String commentContent,
      HttpSession session) throws DiaryException {
    // 유효성 검사
    if (commentContent.equals("") || commentContent == null) {
      return new ResponseEntity<>("댓글내용이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 로그인 세션 데이터
    String loginedId = (String) session.getAttribute("loginInfo");
    Client client = new Client();
    client.setClientId(loginedId);
    Comment comment = new Comment();
    comment.setClient(client);
    comment.setDiaryNo(diaryNo);
    comment.setCommentContent(commentContent);
    try {
      diaryService.writeComment(comment);
      return new ResponseEntity<>(diaryNo + "번 다이어리의 댓글작성을 성공했습니다.", HttpStatus.OK);
    } catch (AddException e) {
      e.printStackTrace();
      return new ResponseEntity<>("서버오류 - 내용 : " + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR); // 500에러
    }
  }

  @PutMapping(value = "diary/{diaryNo}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> modifyComment(@PathVariable int diaryNo,
      @RequestBody Comment comment, HttpSession session) throws DiaryException {
    String loginedId = (String) session.getAttribute("loginInfo");
    try {
      if (loginedId.equals(comment.getClient().getClientId())) {
        diaryService.modifyComment(comment);
      } else {
        return new ResponseEntity<>("자신의 댓글만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST); // 400에러
      }
      return new ResponseEntity<>(diaryNo + "번 다이어리의 댓글수정을 성공했습니다.", HttpStatus.OK);
    } catch (ModifyException e) {
      e.printStackTrace();
      return new ResponseEntity<>("서버오류 - 내용 : " + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR); // 500에러
    }
  }

  @DeleteMapping(value = "diary/{diaryNo}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeComment(@PathVariable int diaryNo, @RequestBody Comment comment,
      HttpSession session) throws DiaryException {

    String loginedId = (String) session.getAttribute("loginInfo");

    try {
      if (loginedId.equals(comment.getClient().getClientId())) {
        diaryService.removeComment(diaryNo, comment.getCommentNo());
      } else {
        return new ResponseEntity<>("자신의 댓글만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(diaryNo + "번 다이어리의 댓글삭제를 성공했습니다.", HttpStatus.OK);
    } catch (RemoveException e) {
      e.printStackTrace();
      return new ResponseEntity<>("서버오류 - 내용 : " + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
