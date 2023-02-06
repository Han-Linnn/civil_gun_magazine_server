package com.jingde.equipment.app.post.controller;

import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.post.dto.PostDTO;
import com.jingde.equipment.app.post.service.PostService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author
 *
 * 职位管理(关联直接的上级)
 */
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Resource
    private PostService postService;

    /**
     * 职务列表
     * /api/post/page
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/page")
    @LoginRequired
    public Result listByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String parentId,String levelName) {
        PageInfo pageInfo = postService.selectPage(page, size, parentId,levelName);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 职务筛选
     * /api/post/select
     *
     * @return
     */
    @GetMapping("/select")
    @LoginRequired
    public Result postSelect() {
        List<Post> list = postService.select();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 职务添加
     * /api/post/info
     *
     * @param form
     * @return
     */
    @PostMapping("/info")
    @LoginRequired
    public Result postAdd(@RequestBody @Validated PostDTO form) {
        if (StringUtils.isBlank(form.getPostName()))
            return ResultGenerator.genFailResult("职务名为空");
        Post p = new Post();
        p.setId(form.getId());
        p.setPostName(form.getPostName());
        p.setParentPostId(form.getParentPostId());
        p.setPostLevel(0);
        if (null != form.getParentPostId()) {
            //查询职务是否存在
            Post parentPost = postService.getById(form.getParentPostId());
            if (null == parentPost)
                return ResultGenerator.genFailResult("上级职务不存在,请重新选择");
            p.setParentPostName(parentPost.getPostName());
            p.setPostLevel(parentPost.getPostLevel() + 1);
        }
        postService.save(p);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 职务删除
     * /api/post/info/{id}
     *
     * @param id 职务id
     * @return
     */
    @DeleteMapping("/info/{id}")
    @LoginRequired
    public Result postDelete(@PathVariable @NotNull(message = "id不能为空") String id) {
        if (StringUtils.isBlank(id))
            return ResultGenerator.genFailResult("id为空");
        postService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 职务更新
     * /api/post/info
     *
     * @param form
     * @return
     */
    @PutMapping("/info")
    @LoginRequired
    public Result postUpdate(@RequestBody @Validated PostDTO form) {
        if (null == form.getId() || 0 == form.getId())
            return ResultGenerator.genFailResult("id为空");
        if (StringUtils.isBlank(form.getPostName()))
            return ResultGenerator.genFailResult("职务名为空");
        Post p = new Post();
        p.setId(form.getId());
        p.setPostName(form.getPostName());
        p.setParentPostId(form.getParentPostId());
        p.setPostLevel(0);
        if (null != form.getParentPostId()) {
            //查询职务是否存在
            Post parentPost = postService.getById(form.getParentPostId());
            if (null == parentPost)
                return ResultGenerator.genFailResult("上级职务不存在,请重新选择");
            p.setParentPostName(parentPost.getPostName());
            p.setPostLevel(parentPost.getPostLevel() + 1);
        }

        postService.updateAllById(p);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 职务详情
     * /api/post/info/{id}
     *
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @LoginRequired
    public Result postDetail(@PathVariable @NotNull(message = "id不能为空") Integer id) {
        Post info = postService.getById(id);
        return ResultGenerator.genSuccessResult(info);
    }
}
