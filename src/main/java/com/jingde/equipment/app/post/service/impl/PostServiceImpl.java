package com.jingde.equipment.app.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.post.dao.PostMapper;
import com.jingde.equipment.app.post.service.PostService;
import com.jingde.equipment.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final PostMapper postMapper;

    @Override
    public PageInfo selectPage(Integer page,  Integer size,String parentId,String postName) {
        PageHelper.startPage(page, size);
        List<Post> list = postMapper.selectListByCondition(parentId,postName);
        return new PageInfo(list);
    }


    @Override
    public void updateAllById(Post post) {
        postMapper.updateById(post);
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("parent_post_id",post.getId());
        queryWrapper.allEq(queryMap);
        Post p = new Post();
        p.setParentPostName(post.getPostName());
        postMapper.update(p,queryWrapper);

    }

    @Override
    public List<Post> select() {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        return postMapper.selectList(queryWrapper);
    }
}
