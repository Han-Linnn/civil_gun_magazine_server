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
            throw new ServiceException("????????????");
        }
        if (StringUtils.isBlank(form.getPoliceCode())) {
            throw new ServiceException("????????????");
        }
        if (null != policeMapper.selectByPoliceCodeOrId(form.getPoliceCode(), null)) {
            throw new ServiceException("?????????????????????");
        }
        if (StringUtils.isBlank(form.getPassword())) {
            form.setPassword("123456");
        }
        if (StringUtils.isBlank(form.getDepartId())) {
            throw new ServiceException("??????id??????");
        }
        if (null == form.getPostId()) {
            throw new ServiceException("??????id??????");
        }
        try {
            //??????????????????,????????????
            DepartmentVO dept = departmentMapper.findById(Integer.valueOf(form.getDepartId()));
            if (null != dept) {
                form.setDepartmentName(dept.getDepartmentName());
            } else {
                throw new ServiceException("???????????????,???????????????");
            }
            Post post = postMapper.selectById(form.getPostId());
            if (null == post) {
                throw new ServiceException("???????????????,???????????????");
            }
            form.setPostName(post.getPostName());
            //????????????
            policeMapper.add(form);
            //????????????id
            Police police = policeMapper.selectByPoliceCodeOrId(form.getPoliceCode(), null);
            //????????????
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
            //????????????
            if (StringUtils.isNotBlank(form.getIsSon()) && "0".equals(form.getIsSon())) {
                u.setIsSubAccount(0);
                u.setIsSon(0);
            } else {
                u.setIsSon(1);
            }
            //????????????????????????
            userMapper.add(u);
            //??????????????????
            PoliceEquipmenused model = new PoliceEquipmenused();
            model.setPoliceCode(form.getPoliceCode());
            model.setStatus(1);
            policeEquipmenusedMapper.saveOrUpdateBypoliceCode(model);
            //???????????????????????????
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
                //?????????????????????
                userMapper.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            policeMapper.deleteById(form.getId());
            return ResultGenerator.genFailResult("???????????????");
        }
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result delete(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("id??????");
        }
        Police police = policeMapper.selectByPoliceCodeOrId(null, id + "");
        try {
            //????????????
            policeMapper.deleteById(id);

            //??????????????????????????????
            userMapper.deleteByPoliceCode(police.getPoliceCode());
        } catch (Exception e) {
            return ResultGenerator.genFailResult("???????????????");
        }
        return ResultGenerator.genSuccessResult();

    }

    @Override
    public Police findByPoliceCode(String policeCode, String policeName) {

        if (StringUtils.isBlank(policeCode) && StringUtils.isBlank(policeName)) {
            throw new ServiceException("??????????????????????????????????????????");
        }
        Police police = policeMapper.selectByPoliceCode(policeCode, policeName);
        if (null == police) { //????????????????????????,???????????????
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
            throw new ServiceException("???????????????");
        }
        if (null == status || (0 != status && 1 != status)) {
            status = 1;
        }
        policeMapper.updateUseGunStatus(id, status);
        if (0 == status) { //????????????
            //????????????????????????????????????
            policeEquipmenusedMapper.deleteByPoliceId(id);
        }
    }

    @Override
    public List<UserTypeVO> selectType(String policeCode, String policeName, Integer type) {

        List<UserTypeVO> list = new ArrayList<>();

        if (1 == type) {//1=????????????
            list = userMapper.selectUserType(policeCode, policeName, "/firearms/repair");
        } else if (2 == type) {//2=????????????(??????????????????????????????)
            //????????????
            list = userMapper.selectUserType(policeCode, policeName, "/firearms/usedlog/examine");
            //????????????
        } else if (3 == type) { //3=??????????????????

            throw new ServiceException("??????????????????,?????????");
        } else {//0=????????????(??????,?????????0)
            //?????????????????????0,?????????????????????
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
            throw new ServiceException("id??????");
        }
        PoliceInfoVO info = policeMapper.findInfoById(id);
        //????????????????????????,???????????????
        if (null == info) {
            return new PoliceInfoVO();
        } else {
            UserSonVO userSonVO = selectSonAccount(info.getPoliceCode());
            if (null != userSonVO) {
                info.setSubAccount(userSonVO.getAccount());//?????????
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
            //??????????????????,????????????
            DepartmentVO dept = departmentService.departmentInfo(Integer.parseInt(form.getDepartId()));
            if (null != dept) {
                form.setDepartmentName(dept.getDepartmentName());
            } else {
                throw new ServiceException("???????????????,???????????????");
            }
        }
        Post post = postMapper.selectById(form.getPostId());
        if (null == post) {
            throw new ServiceException("???????????????,???????????????");
        }
        form.setPostName(post.getPostName());
        //????????????
        policeMapper.updatePolice(form);
        //3??????t_user
        UserDTO user = new UserDTO();
        user.setPoliceCode(form.getPoliceCode());
        user.setPoliceName(form.getPoliceName());
        user.setPoliceId(form.getId() + "");
        user.setHomePage(form.getHomePage() == null ? "0" : form.getHomePage());

        //????????????
        if (StringUtils.isNotBlank(form.getIsSon()) && "0".equals(form.getIsSon())) {
            user.setIsSon(0);
            //?????????????????????????????????????????????????????????????????????????????????
            UserSonVO userSonVO = selectSonAccount(form.getPoliceCode());
            if (null != userSonVO) { //?????????????????????
                user.setId(userSonVO.getId());
                user.setSubAccount(form.getSubAccount());//?????????
                user.setSubPassword(form.getSubPassword());//???????????????
                //???????????????????????????
                userMapper.updateUserById(user);
            } else { //???????????????
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
                //?????????????????????
                userMapper.add(u);
            }
        } else { //???????????????
            user.setIsSon(1);
            //?????????????????????????????????????????????????????????????????????????????????
            UserSonVO userSonVO = selectSonAccount(form.getPoliceCode());
            //????????????????????????????????????????????????????????????
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
                throw new ServiceException("?????????????????????");
            }
            //??????????????????????????????id
            if (StringUtils.isNotBlank(po.getDepartmentName())) {
                String departmentId = departmentService.selectIdByName(po.getDepartmentName());
                //???????????????????????????
                if (null == departmentId) {
                    throw new ServiceException("??????(" + po.getDepartmentName() + ")?????????");
                }
                po.setDepartId(departmentId);

            } else {
                throw new ServiceException("??????????????????");
            }
            if (StringUtils.isNotBlank(po.getPostName())) {
                String postId = postMapper.selectIdByName(po.getPostName());
                //???????????????????????????
                if (null == postId) {
                    throw new ServiceException("??????(" + po.getPostName() + ")?????????");
                }
                po.setPostId(postId);
            } else {
                throw new ServiceException("??????????????????");
            }
        }
        for (PoliceDTO po : list) {
            add(po);
        }
    }
}
