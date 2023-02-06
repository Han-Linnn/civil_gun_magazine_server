package com.jingde.equipment.app.cabinets.vo;

import com.jingde.equipment.app.cabinets.vo.LableVO;

import java.util.List;

/**
 * @Description: com.jingde.equipment.app.system.vo
 * @Title: TypeVO
 * @Auther: CzSix
 * @create 2019/3/12 10:04
 * 枪炮变更,弹药变更列表返回映射
 */
public class TypeVO {
    private String id;
    private String cause;
    private Integer total;
    private Integer changeTotal;
    private Integer bombTotal; //炮弹数量
    private Integer bombChange; //炮弹变更数量
    private Integer bulletTotal; //子弹数量
    private Integer bulletChange;  //子弹变更数量
    private List<LableVO> content;
    private String registerName;
    private String registerDate;
    private String text;
    private String type;
    private int isFirst;

    public Integer getBombChange() {
        return bombChange;
    }

    public void setBombChange(Integer bombChange) {
        this.bombChange = bombChange;
    }

    public Integer getBulletTotal() {
        return bulletTotal;
    }

    public void setBulletTotal(Integer bulletTotal) {
        this.bulletTotal = bulletTotal;
    }

    public Integer getBulletChange() {
        return bulletChange;
    }

    public void setBulletChange(Integer bulletChange) {
        this.bulletChange = bulletChange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public List<LableVO> getContent() {
        return content;
    }

    public void setContent(List<LableVO> content) {
        this.content = content;
    }


    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getChangeTotal() {
        return changeTotal;
    }

    public void setChangeTotal(Integer changeTotal) {
        this.changeTotal = changeTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getBombTotal() {
        return bombTotal;
    }

    public void setBombTotal(Integer bombTotal) {
        this.bombTotal = bombTotal;
    }
}
