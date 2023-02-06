package com.jingde.equipment.app.apply.vo;

import com.jingde.equipment.app.firearms.vo.ApplyDetails;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @auther XC
 * @create 2020-07-15 15:01
 */
@Data
public class FirearmsLogVO {

	private Integer id;
	private String applyDate;
	private String lastReceiveTime;
	private String lastReturnTime;
	private List<ApplyDetails> firearmDeatilsList;
	private List<ApplyDetails> ammoDeatilsList;

}
