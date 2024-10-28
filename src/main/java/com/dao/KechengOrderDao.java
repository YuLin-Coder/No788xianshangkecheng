package com.dao;

import com.entity.KechengOrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.KechengOrderView;

/**
 * 课程订单 Dao 接口
 *
 * @author 
 * @since 2021-04-13
 */
public interface KechengOrderDao extends BaseMapper<KechengOrderEntity> {

   List<KechengOrderView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
