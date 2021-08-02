package com.lovecoding.test;

import com.lovecoding.mapper.BmsBookMapper;
import com.lovecoding.mapper.OmsOrderMapper;
import com.lovecoding.mapper.UmsUserMapper;
import com.lovecoding.pojo.*;
import com.lovecoding.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 只要是针对于单表的CURD操作，都可以使用逆向工程。
 */
public class QuickTest {

    @Test
    public void testFn(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();

        UmsUserMapper userMapper = sqlSession.getMapper(UmsUserMapper.class);
        //根据ID查询用户
        UmsUser umsUser = userMapper.selectByPrimaryKey(3);//根据主键查询一条记录
        System.out.println(umsUser);

        //根据名字和性别查询用户
        UmsUserExample uue = new UmsUserExample();
        uue.clear();//清空之前组建好的条件
        uue.createCriteria().andNameLike("%" + "飞" + "%").andSexEqualTo(0);

        List<UmsUser> umsUserList = userMapper.selectByExample(uue);
        System.out.println(umsUserList);
        System.out.println("---------------------------");
        //需要：查询用户名为 'zhangsan' 或者 名称为 '诸葛亮'
        //或者  ： 1、直接使用or()方法再拼下一个条件。或者直接使用or(条件)
        uue.clear();
        /*uue.createCriteria().andUsernameEqualTo("zhangsan");
        uue.or().andNameEqualTo("诸葛亮");*/
        uue.createCriteria().andUsernameEqualTo("zhangsan");
        uue.or(uue.createCriteria().andNameEqualTo("诸葛亮"));
        List<UmsUser> umsUserList1 = userMapper.selectByExample(uue);
        System.out.println(umsUserList1);

        System.out.println("---------------------------------------------");
        uue.clear();
        uue.setOrderByClause("amount DESC");
        List<UmsUser> umsUserList2 = userMapper.selectByExample(uue);
        System.out.println(umsUserList2);

    }

    /**
     * 图书相关修改
     */
    @Test
    public void testFn1(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BmsBookMapper bookMapper = sqlSession.getMapper(BmsBookMapper.class);
        //第一种方式 ，需要对象中内容全部传递才可以
        BmsBook bmsBook = new BmsBook();
        bmsBook.setBookAuthor("罗贯中");
        bmsBook.setBookName("三国演义");
        bmsBook.setId(3);
        System.out.println("-----------------------");


        //第二种方式：先根据主键查询出来对象信息。再修改值
        BmsBook bmsBook1 = bookMapper.selectByPrimaryKey(3);
        bmsBook1.setBookName("红楼梦");

        int rows = bookMapper.updateByPrimaryKey(bmsBook1);
        System.out.println(rows);
        System.out.println("-----------------------");
        //第三种方式： 根据条件直接修改
        BmsBookExample bbe = new BmsBookExample();
        bbe.createCriteria().andBookNameEqualTo("红楼梦");

        BmsBook bmsBook2 = new BmsBook();
        bmsBook2.setBookPrice("2000");

        bookMapper.updateByExampleSelective(bmsBook2 , bbe);
    }

    /**
     * 订单删除、添加相关
     */
    @Test
    public void testFn2(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        OmsOrderMapper omsOrderMapper = sqlSession.getMapper(OmsOrderMapper.class);
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOno("bbb1223232");
        omsOrder.setTotalAmount("55");

        //增加订单
        omsOrderMapper.insert(omsOrder);

        //删除订单
        int rows = omsOrderMapper.deleteByPrimaryKey(5L);
        System.out.println(rows);

    }

    @Test
    public void testFn3(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        OmsOrderMapper omsOrderMapper = sqlSession.getMapper(OmsOrderMapper.class);
        List<Long> ids = new ArrayList<>();
        ids.add(3L);
        ids.add(6L);
        OmsOrderExample ooe = new OmsOrderExample();
        ooe.createCriteria().andOidIn(ids);

        int rows = omsOrderMapper.deleteByExample(ooe);

        System.out.println(rows);

    }
}
