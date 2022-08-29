package com.routediary.control;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CommentController {
  private Logger logger = Logger.getLogger(getClass());
  
  @Autowired
  private CommentService service;
  
  @Autowired
  private ServletContext sc;
  
  @GetMapping("")
}
