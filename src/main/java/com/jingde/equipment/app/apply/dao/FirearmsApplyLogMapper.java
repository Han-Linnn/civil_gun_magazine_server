package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsApplyLogDTO;
import com.jingde.equipment.app.apply.dto.FirearmsApplyLogForm;
import com.jingde.equipment.app.apply.vo.FirearmsApplyLogVo;
import com.jingde.equipment.app.apply.vo.FirearmsLogDetailVO;
import com.jingde.equipment.app.apply.vo.FirearmsLogVO;
import com.jingde.equipment.model.FirearmsApplyLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请记录表 Mapper 接口
 *
 * @author termite
 * @since 2020-07-08
 */
public interface FirearmsApplyLogMapper extends BaseMapper<FirearmsApplyLog> {

    Integer insertByForm(@Param("form") FirearmsApplyLogForm form);

    Integer selectMaxId();

    void updateFormById(@Param("form")FirearmsApplyLogForm form);

    void updateApprovalById(@Param("form")FirearmsApplyLogForm form);

    FirearmsApplyLogVo findInfoById(@Param("id")Integer id);

    List<FirearmsApplyLogVo> selectPageByCondition(@Param("dto")FirearmsApplyLogDTO dto);

    void updateStatusById(@Param("id")Integer id,@Param("status") String status);

	List<FirearmsLogVO> getFirearmsLog();

	List<FirearmsLogDetailVO> getFirearmsLogDetail(@Param("id") Integer id);
}
