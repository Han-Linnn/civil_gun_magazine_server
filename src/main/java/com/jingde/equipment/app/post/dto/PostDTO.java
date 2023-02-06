package com.jingde.equipment.app.post.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class PostDTO {

    private Integer id;
    private Integer parentPostId;
    @NotNull(message = "postName不能为空")
    private String postName;

}