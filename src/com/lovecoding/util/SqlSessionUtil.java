package com.lovecoding.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {

    public static SqlSession getSqlSession(){
        try {
            //1、获取到主核心配置文件的位置
            String res = "sqlMapConfig.xml";
            //2、根据核心配置文件生成输入流
            InputStream inputStream = null;
            inputStream = Resources.getResourceAsStream(res);
            //3、根据输入流创建SQL会话工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            //4、由会话工厂对象来开启会话  - 默认是false 只能执行查询R操作语句。不开启事务。置为true真正开启事务、可执行CUD操作
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            return sqlSession;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
