package com.jingde.equipment.app.firearms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.firearms.dto.AmmoTypeDTO;
import com.jingde.equipment.app.firearms.vo.AmmoFirearmVO;
import com.jingde.equipment.app.firearms.vo.AmmoTypeVO;
import com.jingde.equipment.model.AmmoType;
import com.jingde.equipment.app.firearms.vo.TypeTitleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 弹药类型Mapper
 *
 * @author hjy
 */
public interface AmmoTypeMapper extends BaseMapper<AmmoType> {

    List<TypeTitleVO> findByStatus(@Param("status") String status);

    /**
     * 弹药类型列表
     *
     * @return
     */
    List<AmmoTypeVO> findAmmoType();

    /**
     * 弹药对应的枪支类型列表
     *
     * @return
     */
    List<AmmoFirearmVO> findAmmoFirearmType(@Param("ammoId") Integer ammoId);

    /**
     *批量减少弹药数量
     */
    void updateReduceCountByList(@Param("list")List<AmmoTypeDTO> list);

    /**
     * 批量增加弹药数量
     * @param list
     */
    void updateIncreaseCountByList(@Param("list") List<AmmoTypeDTO> list);

    void updateManyById(@Param("form")AmmoTypeDTO form);
}
