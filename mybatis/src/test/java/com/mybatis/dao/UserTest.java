package com.mybatis.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTest {
    String resource = "SqlMapConfig.xml";
    Reader reader;
    {
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(reader);
    @Test
    public void insert(){
        // 获得 sqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取唯一标识
        String path = "mapper.insert";
        // 要传入的参数
        User user = new User();
        user.setName("UserTest");
        user.setPassword("123456");
        user.setAddress("广东广州");
        System.out.println(user.toString());
        // 执行sql语句
        sqlSession.insert(path,user);
        // 手动提交事务
        sqlSession.commit();
        System.out.println("新增成功");
        // 关闭sqlSession
        sqlSession.close();
    }
    @Test
    public void delete(){
        SqlSession sqlSession = factory.openSession();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        sqlSession.delete("mapper.delete", list);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void update(){
        SqlSession sqlSession = factory.openSession();
        User user = new User();
        user.setName("NewUserTest");
        user.setPassword("123");
        user.setAddress("四川成都");
        sqlSession.update("mapper.update",user);

        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void select(){
        SqlSession sqlSession = factory.openSession();
        Map<String,String> map = new HashMap<String, String>();
        map.put("id", "1");
        map.put("name","User%");
        List<User> list = sqlSession.selectList("mapper.select", map);
        sqlSession.commit();
        for (User user : list) {
            System.out.println(user);
        }
        sqlSession.close();
    }
}
