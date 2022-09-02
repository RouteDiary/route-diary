package com.routediary.control;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.routediary.dto.Notice;
import com.routediary.dto.PageBean;
import com.routediary.dto.ResultBean;

@RestControllerAdvice
@RequestMapping("notice/*")
public class NoticeController {
//  @GetMapping(value= {"notice/list","notice/list/{currentPageOpt}"})
//  public ResultBean<PageBean<Notice>> showDiaryBoard(@PathVariable Optional<Integer> currentPageOpt){
//    
//    ResultBean<PageBean<Notice>> resultBean = new ResultBean<PageBean<Notice>>();
//    int currentPage;
//    int order;
//    
//    PageBean<Notice> notices = adminService.showDiaryBoard(int order, currentPage, null);
//    return null;
//    
//  }
}
