package com.jingde.equipment.app.user.vo;

import lombok.Data;


@Data
public class UserSonVO  {
  private String enableSubAccount; //是否启用子账号(0:启用,1:不启用(默认))
  private String account;
  private String id;
  //授权的账号id
  private String subAccountId;
}
