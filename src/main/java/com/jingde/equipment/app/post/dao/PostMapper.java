package com.jingde.equipment.app.post.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface PostMapper extends BaseMapper<Post> {

    List<Post> selectListByCondition(@Param("parentId") String parentId,@Param("postName") String postName);

    String selectIdByName(@Param("postName") String postName);
}
