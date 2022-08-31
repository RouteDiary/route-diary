package com.routediary.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.routediary.dto.Client;
import com.routediary.dto.ResultBean;
import com.routediary.exception.AddException;
import com.routediary.exception.FindException;
import com.routediary.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
  
  @Autowired
  public ClientService clientService;
  
  @PostMapping("/signup")
  public ResultBean<Client> signup(Client client) throws AddException{
    ResultBean<Client> resultBean = new ResultBean();
    clientService.signup(client);
    resultBean.setStatus(1);
    resultBean.setMessage("가입성공");
    return resultBean;
  }
  
  @GetMapping("/login")
  public ResultBean<Client> login(@RequestParam(name ="clientId", required = true) String clientId) throws FindException{
    ResultBean<Client> resultBean = new ResultBean();
    clientService.login(clientId);
    resultBean.setStatus(1);
    resultBean.setMessage("로그인 성공");
    return resultBean;
  
  }
  
}
