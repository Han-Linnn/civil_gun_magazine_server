package com.jingde.equipment.app.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.police.dto.PoliceDTO;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.model.User;
import com.jingde.equipment.app.system.vo.UserTypeVO;
import com.jingde.equipment.app.user.dto.UserDTO;
import com.jingde.equipment.app.user.vo.UserInfoVO;
import com.jingde.equipment.app.user.vo.UserSonVO;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author
 */
public interface UserMapper extends BaseMapper<User> {
    @Insert("insert into t_user (`account`,`password`,`police_id`,`police_code`,`police_name`,`enable_sub_account`,`is_sub_account`,`is_son`,`home_page`) " +
            "VALUES (#{user.account},#{user.password},#{user.policeId},#{user.policeCode},#{user.policeName},#{user.enableSubAccount},#{user.isSubAccount},#{user.isSon},#{user.homePage})")
    void add(@Param("user") User user);

    @Delete("delete from t_user where police_code = #{policeCode} ")
    void deleteByPoliceCode(@Param("policeCode") String policeCode);

    // 用户详情信息 oceanover
    UserInfoVO findUserById(@Param("userId") int userId);

    @Select("select police_name from t_user where account=#{account}")
	String selectPoliceNameByAccount(@Param("account") String account);

	List<User> dutyUserSelect(@Param("status") Integer status);

	@Select("select id,account,password,police_id,police_code,police_name  from t_user where account=#{account} and status=1")
	@Results({
		@Result(id = true,column = "id",property = "id"),
		@Result(column = "account",property = "account"),
		@Result(column = "password",property = "password"),
		@Result(column = "police_id",property = "policeId"),
		@Result(column = "police_code",property = "policeCode"),
		@Result(column = "police_name",property = "policeName"),
	})
	User getUserMessage(@Param("account") String account);
	
    void deleteByPoliceIds(@Param("ids")String[] ids);

    void updateByPoliceId(@Param("user") UserDTO user);

    List<UserTypeVO> selectUserType(@Param("policeCode")String policeCode, @Param("policeName")String policeName, @Param("url") String url);

    void updatePasswordById(@Param("condition") UserDTO condition);

    UserSonVO selectSonAccount(@Param("policeCode")String policeCode);

    /**
     * 修改子账号状态
     * @param enableSubAccount 状态
     * @param id  id
     * @param password 密码
     */
    @Update("UPDATE t_user SET enable_sub_account = #{enableSubAccount},password = #{password},sub_account_id = #{subAccountId} WHERE id = #{id}")
    void enableSubAccount(@Param("enableSubAccount")Integer enableSubAccount,@Param("id")Integer id,@Param("password")String password,@Param("subAccountId")String subAccountId);

    @Delete("delete from t_user where id = #{id}")
    void deleteSubAccount(@Param("id")String id);

    void updateUserById(@Param("user")UserDTO user);
}
