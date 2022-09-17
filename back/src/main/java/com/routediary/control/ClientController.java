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
import com.routediary.enums.ErrorCode;
import com.routediary.enums.SuccessCode;
import com.routediary.exception.AddException;
import com.routediary.exception.DuplicationException;
import com.routediary.exception.FindException;
import com.routediary.exception.LogoutFailureException;
import com.routediary.exception.MismatchException;
import com.routediary.exception.ModifyException;
import com.routediary.exception.NoPermissionException;
import com.routediary.exception.NotLoginedException;
import com.routediary.exception.RemoveException;
import com.routediary.exception.WithdrawnClientException;
import com.routediary.service.ClientService;

@RestController
@RequestMapping("client/*")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody Client client, HttpSession session)
      throws AddException {
    String clientId = (String) session.getAttribute("loginInfo");
    clientService.signup(client);
    ResultBean<?> resultBean = new ResultBean(SuccessCode.SIGNUP_SUCCESS);
    resultBean.setLoginInfo(clientId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Client client, HttpSession session)
      throws FindException, MismatchException, WithdrawnClientException {
    String loginedId = (String) session.getAttribute("loginInfo");
    String clientId = client.getClientId();
    String clientPwd = client.getClientPwd();
    boolean isLoginSucceeded = clientService.login(clientId, clientPwd);
    if (isLoginSucceeded) {
      session.setAttribute("loginInfo", clientId);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.LOGIN_SUCCESS);
      resultBean.setLoginInfo(loginedId);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    } else {
      throw new FindException();
    }
  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout(HttpSession session) throws LogoutFailureException {
    session.removeAttribute("loginInfo");
    String clientId = (String) session.getAttribute("loginInfo");
    if (clientId == null) {
      ResultBean<?> resultBean = new ResultBean(SuccessCode.LOGOUT_SUCCESS);
      resultBean.setLoginInfo(clientId);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    } else {
      throw new LogoutFailureException(ErrorCode.FAILED_TO_LOGOUT);
    }
  }

  @PutMapping("/modify")
  public ResponseEntity<?> modifyAccount(@RequestBody Client client, HttpSession session)
      throws ModifyException, NotLoginedException, NoPermissionException {
    String clientId = (String) session.getAttribute("loginInfo");
    if (clientId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else if (!clientId.equals(client.getClientId())) {
      throw new NoPermissionException(ErrorCode.NO_PERMISSION);
    } else {
      clientService.modifyAccount(client);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_MODIFY_ACCOUNT);
      resultBean.setLoginInfo(clientId);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @DeleteMapping("/remove")
  public ResponseEntity<?> removeAccount(@RequestBody Client client, HttpSession session)
      throws RemoveException, NotLoginedException, NoPermissionException {
    String clientId = (String) session.getAttribute("loginInfo");
    if (clientId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else if (!clientId.equals(client.getClientId())) {
      throw new NoPermissionException(ErrorCode.NO_PERMISSION);
    } else {
      clientService.removeAccount(client);
      ResultBean<?> resultBean = new ResultBean(SuccessCode.SUCCESS_TO_REMOVE_ACCOUNT);
      resultBean.setLoginInfo(clientId);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }

  @GetMapping("/idcheck")
  public ResponseEntity<?> idDuplicationCheck(@RequestParam String clientId, HttpSession session)
      throws FindException, DuplicationException {
    String loginedId = (String) session.getAttribute("loginInfo");
    clientService.idDuplicationCheck(clientId);
    ResultBean<?> resultBean = new ResultBean(SuccessCode.VAILD_ID);
    resultBean.setLoginInfo(loginedId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @GetMapping("/nicknamecheck")
  public ResponseEntity<?> nicknameDuplicationCheck(@RequestParam String clientNickname,
      HttpSession session) throws FindException, DuplicationException {
    String clientId = (String) session.getAttribute("loginInfo");
    clientService.nicknameDuplicationCheck(clientNickname);
    ResultBean<?> resultBean = new ResultBean(SuccessCode.VAILD_NICKNAME);
    resultBean.setLoginInfo(clientId);
    return new ResponseEntity<>(resultBean, HttpStatus.OK);
  }

  @GetMapping("/clientinfo")
  public ResponseEntity<?> bringClientInfo(HttpSession session)
      throws FindException, NotLoginedException, NoPermissionException {
    String clientId = (String) session.getAttribute("loginInfo");
    if (clientId == null) {
      throw new NotLoginedException(ErrorCode.NOT_LOGINED);
    } else {
      Client client = clientService.bringClientInfo(clientId);
      ResultBean<Client> resultBean =
          new ResultBean<Client>(SuccessCode.SUCCESS_TO_BRING_CLIENT_INFO);
      resultBean.setT(client);
      resultBean.setLoginInfo(clientId);
      return new ResponseEntity<>(resultBean, HttpStatus.OK);
    }
  }
}
