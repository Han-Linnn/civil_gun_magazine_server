package com.jingde.equipment.app.user.vo;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.model.User;
import lombok.Data;

/**
 * Created by oceanover on 2019-03-11.
 */
@Data
@JSONType(serialzeFeatures = SerializerFeature.WriteNullBooleanAsFalse)
//@JSONType(serialzeFeatures = SerializerFeature.WriteMapNullValue)
public class UserInfoVO extends User {
    // 警员信息
    private Police police;
    //子账号信息
    private UserSonVO userSonvo;

}
