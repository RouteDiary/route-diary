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
import com.routediary.service.ClientService;

@RestController
@RequestMapping("client/*")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @PostMapping("/signup")
  public ResultBean<Client> signup(@RequestBody Client client) throws AddException {
    ResultBean<Client> resultBean = new ResultBean();
    try {
      clientService.signup(client);
      resultBean.setStatus(1);
      resultBean.setMessage("회원가입 성공");
    } catch (AddException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage("회원가입 실패");
    }
    return resultBean;
  }

  @GetMapping("/login")
  public ResultBean<Client> login(@RequestParam(name = "clientId", required = true) String clientId,
      @RequestParam(name = "clientPwd", required = true) String clientPwd, HttpSession session)
      throws ClientException {
    ResultBean<Client> resultBean = new ResultBean();
    try {
      clientService.login(clientId, clientPwd);
      resultBean.setStatus(1);
      resultBean.setMessage("로그인 성공");
      session.setAttribute("loginInfo", clientId);
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage("로그인 실패. \n" + e.getMessage());
    }
    return resultBean;
  }

  @GetMapping("/logout")
  public ResponseEntity<Client> logout(HttpSession session) throws ClientException {
    session.removeAttribute("loginInfo");
    String clientId = (String) session.getAttribute("loginInfo");
    if (clientId == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/modify")
  public ResultBean<Client> modifyAccount(@RequestBody Client client) throws ClientException {
    ResultBean<Client> resultBean = new ResultBean();
    try {
      clientService.modifyAccount(client);
      resultBean.setStatus(1);
      resultBean.setMessage("회원정보 수정완료");
    } catch (ModifyException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage("회원정보 수정실패 \n" + e.getMessage());
    }
    return resultBean;
  }

  @DeleteMapping("/remove")
  public ResultBean<Client> removeAccount(@RequestBody Client client) throws ClientException {
    ResultBean<Client> resultBean = new ResultBean();
    try {
      clientService.removeAccount(client);
      resultBean.setStatus(1);
      resultBean.setMessage("회원탈퇴 완료");
    } catch (RemoveException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage("회원탈퇴 실패");
    }
    return resultBean;
  }

  @GetMapping("/idcheck")
  public ResultBean<Client> idDuplicationCheck(@RequestParam String clientId)
      throws ClientException {
    ResultBean<Client> resultBean = new ResultBean();
    try {
      clientService.idDuplicationCheck(clientId);
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(1);
      resultBean.setMessage("사용가능한 아이디입니다.");
      if (clientId != null)
        resultBean.setStatus(0);
      resultBean.setMessage(e.getMessage());
    }
    return resultBean;
  }

  @GetMapping("/nicknamecheck")
  public ResultBean<Client> NicknameDuplicationCheck(@RequestParam String clientNickname)
      throws ClientException {
    ResultBean<Client> resultBean = new ResultBean();
    try {
      clientService.NicknameDuplicationCheck(clientNickname);
      resultBean.setStatus(1);
      resultBean.setMessage("사용가능한 닉네임입니다.");
    } catch (FindException e) {
      e.printStackTrace();
      resultBean.setStatus(0);
      resultBean.setMessage(e.getMessage());
    }
    return resultBean;
  }
}
