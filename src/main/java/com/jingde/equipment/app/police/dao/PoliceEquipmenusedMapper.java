package com.jingde.equipment.app.police.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.police.vo.PolicePageVO;
import com.jingde.equipment.model.PoliceEquipmenused;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface PoliceEquipmenusedMapper extends BaseMapper<PoliceEquipmenused> {

    List<PolicePageVO> selectPageBytype(@Param("type") Integer type);

    void deleteByPoliceId(@Param("id") String id);

    void saveOrUpdateBypoliceCode(@Param("model")PoliceEquipmenused model);

    PolicePageVO selectOneByPoliceCode(@Param("policeCode")String policeCode);

}
