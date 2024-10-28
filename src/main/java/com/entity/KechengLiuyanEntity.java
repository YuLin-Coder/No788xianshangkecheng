package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 课程资讯
 *
 * @author 
 * @email
 * @date 2021-04-13
 */
@TableName("kecheng_liuyan")
public class KechengLiuyanEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public KechengLiuyanEntity() {

	}

	public KechengLiuyanEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")

    private Integer id;


    /**
     * 课程id
     */
    @TableField(value = "kecheng_id")

    private Integer kechengId;


    /**
     * 用户id
     */
    @TableField(value = "yonghu_id")

    private Integer yonghuId;


    /**
     * 资讯内容
     */
    @TableField(value = "kecheng_liuyan_content")

    private String kechengLiuyanContent;


    /**
     * 回复内容
     */
    @TableField(value = "reply_content")

    private String replyContent;


    /**
     * 讨论时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：课程id
	 */
    public Integer getKechengId() {
        return kechengId;
    }


    /**
	 * 获取：课程id
	 */

    public void setKechengId(Integer kechengId) {
        this.kechengId = kechengId;
    }
    /**
	 * 设置：用户id
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 获取：用户id
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 设置：资讯内容
	 */
    public String getKechengLiuyanContent() {
        return kechengLiuyanContent;
    }


    /**
	 * 获取：资讯内容
	 */

    public void setKechengLiuyanContent(String kechengLiuyanContent) {
        this.kechengLiuyanContent = kechengLiuyanContent;
    }
    /**
	 * 设置：回复内容
	 */
    public String getReplyContent() {
        return replyContent;
    }


    /**
	 * 获取：回复内容
	 */

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
    /**
	 * 设置：讨论时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：讨论时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "KechengLiuyan{" +
            "id=" + id +
            ", kechengId=" + kechengId +
            ", yonghuId=" + yonghuId +
            ", kechengLiuyanContent=" + kechengLiuyanContent +
            ", replyContent=" + replyContent +
            ", insertTime=" + insertTime +
            ", createTime=" + createTime +
        "}";
    }
}
