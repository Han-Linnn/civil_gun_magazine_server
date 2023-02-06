package com.jingde.equipment.app.police.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.department.dao.DepartmentMapper;
import com.jingde.equipment.app.department.service.DepartmentService;
import com.jingde.equipment.app.police.dao.PoliceEquipmenusedMapper;
import com.jingde.equipment.app.police.dao.PoliceMapper;
import com.jingde.equipment.app.police.dto.PoliceDTO;
import com.jingde.equipment.app.police.dto.PoliceFormDTO;
import com.jingde.equipment.app.police.service.PoliceService;
import com.jingde.equipment.app.post.dao.PostMapper;
import com.jingde.equipment.app.system.vo.*;
import com.jingde.equipment.app.user.dao.UserMapper;
import com.jingde.equipment.app.user.dto.UserDTO;
import com.jingde.equipment.app.user.vo.UserSonVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.*;
import com.jingde.equipment.util.Encrypt;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PoliceServiceImpl extends ServiceImpl<PoliceMapper, Police> implements PoliceService {

    private final PoliceMapper policeMapper;

    private final UserMapper userMapper;

    private final DepartmentMapper departmentMapper;

    private final PoliceEquipmenusedMapper policeEquipmenusedMapper;

    private final DepartmentService departmentService;

    private final PostMapper postMapper;


    @Override
    public List<PoliceInfoVO> selectListByPage(PoliceFormDTO form) {
        List<PoliceInfoVO> list = new ArrayList<>();
        list = policeMapper.selectListByPage(form);
        return list;
    }

    @Override
    public List<DepartmentPoliceVO> findBydepartmentId(Integer id) {
        return policeMapper.findBydepartmentId(id);
    }

    @Override
    public Result add(PoliceDTO form) {
        if (StringUtils.isBlank(form.getPoliceName())) {
            throw new ServiceException("姓名为空");
        }
        if (StringUtils.isBlank(form.getPoliceCode())) {
            throw new ServiceException("工号为空");
        }
        if (null != policeMapper.selectByPoliceCodeOrId(form.getPoliceCode(), null)) {
            throw new ServiceException("该工号已经存在");
        }
        if (StringUtils.isBlank(form.getPassword())) {
            form.setPassword("123456");
        }
        if (StringUtils.isBlank(form.getDepartId())) {
            throw new ServiceException("部门id为空");
        }
        if (null == form.getPostId()) {
            throw new ServiceException("职务id为空");
        }
        try {
            //查询部门名称,职位名称
            DepartmentVO dept = departmentMapper.findById(Integer.valueOf(form.getDepartId()));
            if (null != dept) {
                form.setDepartmentName(dept.getDepartmentName());
            } else {
                throw new ServiceException("部门不存在,请重新选择");
            }
            Post post = postMapper.selectById(form.getPostId());
            if (null == post) {
                throw new ServiceException("职务不存在,请重新选择");
            }
            form.setPostName(post.getPostName());
            //添加警员
            policeMapper.add(form);
            //获取警员id
            Police police = policeMapper.selectByPoliceCodeOrId(form.getPoliceCode(), null);
            //添加账号
            User u = new User();
            u.setAccount(form.getPoliceCode());
            u.setPoliceCode(form.getPoliceCode());
            u.setPoliceName(form.getPoliceName());
            u.setPoliceId(police.getId());
            u.setPassword(form.getPassword());
            form.setId(police.getId() + "");
            String md5 = Encrypt.encryptByMd5(u.getPassword());
            u.setPassword(md5);
            u.setIsSubAccount(0);
            u.setEnableSubAccount(0);
            u.setHomePage(form.getHomePage() == null ? "0" : form.getHomePage());
            //有子账号
            if (StringUtils.isNotBlank(form.getIsSon()) && "0".equals(form.getIsSon())) {
                u.setIsSubAccount(0);
                u.setIsSon(0);
            } else {
                u.setIsSon(1);
            }
            //添加警员登录账号
            userMapper.add(u);
            //添加配枪人员
            PoliceEquipmenused model = new PoliceEquipmenused();
            model.setPoliceCode(form.getPoliceCode());
            model.setStatus(1);
            policeEquipmenusedMapper.saveOrUpdateBypoliceCode(model);
            //是否需要添加子账号
            if (StringUtils.isNotBlank(form.getIsSon()) && "0".equals(form.getIsSon())) {
                u.setIsSubAccount(1);
                u.setIsSon(1);
                if (StringUtils.isBlank(form.getSubAccount())) {
                    u.setAccount("s" + form.getPoliceCode());
                } else {
                    u.setAccount(form.getSubAccount());
                }
                if (StringUtils.isBlank(form.getSubPassword())) {
                    u.setPassword(form.getPassword());
                } else {
                    u.setPassword(form.getSubPassword());
                }
                u.setPassword(Encrypt.encryptByMd5(u.getPassword()));
                u.setIsSubAccount(1);
                u.setEnableSubAccount(1);
                //添加警员子账号
                userMapper.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            policeMapper.deleteById(form.getId());
            return ResultGenerator.genFailResult("请检查参数");
        }
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result delete(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("id为空");
        }
        Police police = policeMapper.selectByPoliceCodeOrId(null, id + "");
        try {
            //删除警员
            policeMapper.deleteById(id);

            //删除该警员的登录用户
            userMapper.deleteByPoliceCode(police.getPoliceCode());
        } catch (Exception e) {
            return ResultGenerator.genFailResult("请检查参数");
        }
        return ResultGenerator.genSuccessResult();

    }

    @Override
    public Police findByPoliceCode(String policeCode, String policeName) {

        if (StringUtils.isBlank(policeCode) && StringUtils.isBlank(policeName)) {
            throw new ServiceException("警员编码跟警员名称不能都为空");
        }
        Police police = policeMapper.selectByPoliceCode(policeCode, policeName);
        if (null == police) { //避免查询不到警员,空指针异常
            return new Police();
        } else {
            return police;
        }

    }

    @Override
    public List<PolicePageVO> findBydeptId(String deptId) {
        return policeMapper.selectByDepartmentId(deptId);
    }

    @Override
    public List<Police> select(String policeName, String policeCode) {
        return policeMapper.selectByNameOrCode(policeName, policeCode);
    }

    @Override
    public void updateUseGunStatus(String id, Integer status) {
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("请选择人员");
        }
        if (null == status || (0 != status && 1 != status)) {
            status = 1;
        }
        policeMapper.updateUseGunStatus(id, status);
        if (0 == status) { //解除禁枪
            //删除配枪人员列表中的记录
            policeEquipmenusedMapper.deleteByPoliceId(id);
        }
    }

    @Override
    public List<UserTypeVO> selectType(String policeCode, String policeName, Integer type) {

        List<UserTypeVO> list = new ArrayList<>();

        if (1 == type) {//1=保养人员
            list = userMapper.selectUserType(policeCode, policeName, "/firearms/repair");
        } else if (2 == type) {//2=审批人员(审批人员包括被授权人)
            //审批人员
            list = userMapper.selectUserType(policeCode, policeName, "/firearms/usedlog/examine");
            //被授权人
        } else if (3 == type) { //3=被授权人列表

            throw new ServiceException("被授权人列表,待开发");
        } else {//0=用枪人员(不传,默认是0)
            //获取用枪状态为0,并且是持枪人员
            list = policeMapper.selectUseGunPoliceBypoliceName(policeCode, policeName);
        }
        return list;
    }

    @Override
    public void updateUseGunStatusByPeuId(String ids, Integer status) {
        policeMapper.updateUseGunStatusByPeuId(ids.split(","), status, 1);
    }

    @Override
    public void updateDepartmentByDeptId(Department dept) {
        policeMapper.updateDepartmentByDeptId(dept);
    }


    @Override
    public PoliceInfoVO findInfoById(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("id为空");
        }
        PoliceInfoVO info = policeMapper.findInfoById(id);
        //避免查询不到警员,空指针异常
        if (null == info) {
            return new PoliceInfoVO();
        } else {
            UserSonVO userSonVO = selectSonAccount(info.getPoliceCode());
            if (null != userSonVO) {
                info.setSubAccount(userSonVO.getAccount());//子账号
            }
            return info;
        }

    }

    @Override
    public UserSonVO selectSonAccount(String policeCode) {
        return userMapper.selectSonAccount(policeCode);
    }

    @Override
    public Result updatePolice(PoliceDTO form) {
        if (null != form.getDepartId()) {
            //查询部门名称,职位名称
            DepartmentVO dept = departmentService.departmentInfo(Integer.parseInt(form.getDepartId()));
            if (null != dept) {
                form.setDepartmentName(dept.getDepartmentName());
            } else {
                throw new ServiceException("部门不存在,请重新选择");
            }
        }
        Post post = postMapper.selectById(form.getPostId());
        if (null == post) {
            throw new ServiceException("职务不存在,请重新选择");
        }
        form.setPostName(post.getPostName());
        //更新警员
        policeMapper.updatePolice(form);
        //3更新t_user
        UserDTO user = new UserDTO();
        user.setPoliceCode(form.getPoliceCode());
        user.setPoliceName(form.getPoliceName());
        user.setPoliceId(form.getId() + "");
        user.setHomePage(form.getHomePage() == null ? "0" : form.getHomePage());

        //有子账号
        if (StringUtils.isNotBlank(form.getIsSon()) && "0".equals(form.getIsSon())) {
            user.setIsSon(0);
            //有子账号，查询出原先的子账号，看需不需要修改账号和密码
            UserSonVO userSonVO = selectSonAccount(form.getPoliceCode());
            if (null != userSonVO) { //直接更新子账号
                user.setId(userSonVO.getId());
                user.setSubAccount(form.getSubAccount());//子账号
                user.setSubPassword(form.getSubPassword());//子账号密码
                //修改子账号相关信息
                userMapper.updateUserById(user);
            } else { //新增子账号
                User u = new User();
                u.setPoliceCode(form.getPoliceCode());
                u.setPoliceName(form.getPoliceName());
                u.setPoliceId(Integer.parseInt(form.getId()));
                u.setIsSon(1);
                u.setIsSubAccount(1);
                u.setEnableSubAccount(1);
                if (StringUtils.isBlank(form.getSubAccount())) {
                    u.setAccount("s" + form.getPoliceCode());
                } else {
                    u.setAccount(form.getSubAccount());
                }
                if (StringUtils.isBlank(form.getSubPassword())) {
                    u.setPassword(Encrypt.encryptByMd5("123456"));
                } else {
                    u.setPassword(Encrypt.encryptByMd5(form.getSubPassword()));
                }
                //添加警员子账号
                userMapper.add(u);
            }
        } else { //没有子账号
            user.setIsSon(1);
            //有子账号，查询出原先的子账号，看需不需要修改账号和密码
            UserSonVO userSonVO = selectSonAccount(form.getPoliceCode());
            //如果选择没有子账户，删除原先的子账号数据
            if (null != userSonVO) {
                userMapper.deleteSubAccount(userSonVO.getId());
            }
        }
        userMapper.updateByPoliceId(user);
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public void importPolice(List<PoliceDTO> list) {
        System.out.println(list.toString());
        for (PoliceDTO po : list) {
            if (StringUtils.isBlank(po.getMobile())) {
                throw new ServiceException("手机号不能为空");
            }
            //通过部门名称得到部门id
            if (StringUtils.isNotBlank(po.getDepartmentName())) {
                String departmentId = departmentService.selectIdByName(po.getDepartmentName());
                //部门不存在抛出提示
                if (null == departmentId) {
                    throw new ServiceException("部门(" + po.getDepartmentName() + ")不存在");
                }
                po.setDepartId(departmentId);

            } else {
                throw new ServiceException("部门不能为空");
            }
            if (StringUtils.isNotBlank(po.getPostName())) {
                String postId = postMapper.selectIdByName(po.getPostName());
                //职务不存在抛出提示
                if (null == postId) {
                    throw new ServiceException("职务(" + po.getPostName() + ")不存在");
                }
                po.setPostId(postId);
            } else {
                throw new ServiceException("职务不能为空");
            }
        }
        for (PoliceDTO po : list) {
            add(po);
        }
    }
}
