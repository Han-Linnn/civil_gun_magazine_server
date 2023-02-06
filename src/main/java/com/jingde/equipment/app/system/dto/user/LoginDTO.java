package com.jingde.equipment.app.system.dto.user;

import com.jingde.equipment.model.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by oceanover on 2019-03-11.
 * @author
 */
@Data
public class LoginDTO extends User {
  // 账号
  @NotNull(message = "account不能为空")
  private String account;
  // 密码
  @Size(min = 6, max = 16,message = "密码长度为6---16位")
  private String password;
}
