package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;

import com.entity.JiaoshiEntity;
import com.service.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;

import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.KechengOrderEntity;

import com.entity.view.KechengOrderView;
import com.entity.KechengEntity;
import com.entity.YonghuEntity;

import com.utils.PageUtils;
import com.utils.R;

/**
 * 课程订单
 * 后端接口
 * @author
 * @email
 * @date 2021-04-13
*/
@RestController
@Controller
@RequestMapping("/kechengOrder")
public class KechengOrderController {
    private static final Logger logger = LoggerFactory.getLogger(KechengOrderController.class);

    @Autowired
    private KechengOrderService kechengOrderService;

    @Autowired
    private JiaoshiService jiaoshiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;



    //级联表service
    @Autowired
    private KechengService kechengService;
    @Autowired
    private YonghuService yonghuService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && "学生".equals(role)){
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        }if(StringUtil.isNotEmpty(role) && "教师".equals(role)){
            params.put("jiaoshiId",request.getSession().getAttribute("userId"));
        }
        params.put("orderBy","id");
        PageUtils page = kechengOrderService.queryPage(params);

        //字典表数据转换
        List<KechengOrderView> list =(List<KechengOrderView>)page.getList();
        for(KechengOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        KechengOrderEntity kechengOrder = kechengOrderService.selectById(id);
        if(kechengOrder !=null){
            //entity转view
            KechengOrderView view = new KechengOrderView();
            BeanUtils.copyProperties( kechengOrder , view );//把实体数据重构到view中

            //级联表
            KechengEntity kecheng = kechengService.selectById(kechengOrder.getKechengId());
            if(kecheng != null){
                BeanUtils.copyProperties( kecheng , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setKechengId(kecheng.getId());
            }
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(kechengOrder.getYonghuId());
            if(yonghu != null){
                BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody KechengOrderEntity kechengOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,kechengOrder:{}",this.getClass().getName(),kechengOrder.toString());
        kechengOrder.setInsertTime(new Date());
        kechengOrder.setCreateTime(new Date());
        kechengOrderService.insert(kechengOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody KechengOrderEntity kechengOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,kechengOrder:{}",this.getClass().getName(),kechengOrder.toString());
        kechengOrderService.updateById(kechengOrder);//根据id更新
        return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        kechengOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }



    /**
    * 前端列表
    */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && "学生".equals(role)){
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        }if(StringUtil.isNotEmpty(role) && "教师".equals(role)){
            params.put("jiaoshiId",request.getSession().getAttribute("userId"));
        }
        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = kechengOrderService.queryPage(params);

        //字典表数据转换
        List<KechengOrderView> list =(List<KechengOrderView>)page.getList();
        for(KechengOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        KechengOrderEntity kechengOrder = kechengOrderService.selectById(id);
            if(kechengOrder !=null){
                //entity转view
        KechengOrderView view = new KechengOrderView();
                BeanUtils.copyProperties( kechengOrder , view );//把实体数据重构到view中

                //级联表
                    KechengEntity kecheng = kechengService.selectById(kechengOrder.getKechengId());
                if(kecheng != null){
                    BeanUtils.copyProperties( kecheng , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setKechengId(kecheng.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(kechengOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody KechengOrderEntity kechengOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,kechengOrder:{}",this.getClass().getName(),kechengOrder.toString());
        kechengOrder.setInsertTime(new Date());
        kechengOrder.setCreateTime(new Date());
        YonghuEntity user = yonghuService.selectById((Integer) request.getSession().getAttribute("userId"));
        if(user == null){
            return R.error();
        }
        KechengEntity kecheng = kechengService.selectById(kechengOrder.getKechengId());
        JiaoshiEntity jiaoshi = jiaoshiService.selectById(kecheng.getJiaoshiId());
        if(user.getNewMoney() < kecheng.getKechengMoeny()){
            return R.error("余额不足请充值");
        }
        user.setNewMoney(user.getNewMoney()-kecheng.getKechengMoeny());
        yonghuService.updateById(user);
        jiaoshi.setNewMoney(jiaoshi.getNewMoney()+kecheng.getKechengMoeny());
        jiaoshiService.updateById(jiaoshi);
        kechengOrderService.insert(kechengOrder);
        return R.ok();
    }


}

