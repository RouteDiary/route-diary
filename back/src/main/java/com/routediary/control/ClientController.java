package com.routediary.control;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.routediary.dto.Client;
import com.routediary.dto.ResultBean;
import com.routediary.exception.AddException;
import com.routediary.exception.ClientException;
import com.routediary.exception.FindException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.RemoveException;
import com.routediary.exception.SelectException;
import com.routediary.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @PostMapping("/signup")
  public ResultBean<Client> signup(@RequestBody Client client) throws AddException {
    ResultBean<Client> resultBean = new ResultBean();
    clientService.signup(client);
    resultBean.setStatus(1);
    resultBean.setMessage("가입성공");
    return resultBean;
  }

  @GetMapping("/login")
  public ResultBean<Client> login(@RequestParam(name = "clientId", required = true) String clientId,
      @RequestParam(name = "clientPwd", required = true) String clientPwd, HttpSession session)
      throws FindException, ClientException {
    ResultBean<Client> resultBean = new ResultBean();
    clientService.login(clientId, clientPwd);
    resultBean.setStatus(1);
    resultBean.setMessage("로그인 성공");
    session.setAttribute("loginInfo", clientId);
    return resultBean;

  }

  @GetMapping("/logout")
  public ResponseEntity<Client> logout(HttpSession session) {
    session.removeAttribute("loginInfo");
    String clientId = (String) session.getAttribute("loginInfo");
    if (clientId == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/modify")
  public ResultBean<Client> modifyAccount(Client client) throws ModifyException {
    ResultBean<Client> resultBean = new ResultBean();
    clientService.modifyAccount(client);
    resultBean.setStatus(1);
    resultBean.setMessage("회원정보 수정 성공");
    return resultBean;

  }

  @DeleteMapping("/remove")
  public ResultBean<Client> removeAccount(Client client) throws RemoveException, ModifyException {
    ResultBean<Client> resultBean = new ResultBean();
    clientService.modifyAccount(client);
    resultBean.setStatus(1);
    resultBean.setMessage("로그인 성공");
    return resultBean;

  }

  @GetMapping("/idcheck")
  public ResultBean<Client> idDuplicationCheck(String clientId)
      throws SelectException, FindException {
    ResultBean<Client> resultBean = new ResultBean();
    clientService.idDuplicationCheck(clientId);
    resultBean.setStatus(1);
    resultBean.setMessage("로그인 성공");
    return resultBean;

  }

  @GetMapping("/nicknamecheck")
  public ResultBean<Client> NicknameDuplicationCheck(String clientNickname)
      throws FindException, SelectException {
    ResultBean<Client> resultBean = new ResultBean();
    clientService.NicknameDuplicationCheck(clientNickname);
    resultBean.setStatus(1);
    resultBean.setMessage("로그인 성공");
    return resultBean;

  }

}
