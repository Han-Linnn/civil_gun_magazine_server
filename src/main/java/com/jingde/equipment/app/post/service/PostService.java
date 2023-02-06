package com.jingde.equipment.app.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.model.Post;

import java.util.List;

/**
 * @author
 */
public interface PostService extends IService<Post> {

    PageInfo selectPage(Integer page, Integer size,String parentId,String postLevel);

    void updateAllById(Post post);

    List<Post> select();
}
