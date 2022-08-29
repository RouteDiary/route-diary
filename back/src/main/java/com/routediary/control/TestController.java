//package com.routediary.control;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//import com.routediary.service.TestService;
//
//@RestController
//public class TestController {
//
//  @Autowired
//  TestService service;
//
//  @GetMapping("/myba")
//  public ModelAndView myba() {
//    ModelAndView mv = new ModelAndView();
//    int totSize = service.totSize("");
//
//    mv.addObject("totSize", totSize);
//    mv.setViewName("myba_result");
//    return mv;
//  }
//}
