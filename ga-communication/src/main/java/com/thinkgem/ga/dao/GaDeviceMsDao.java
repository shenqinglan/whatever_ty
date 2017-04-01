/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.ga.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.thinkgem.ga.entity.GaDeviceMs;




/**
 * 终端DAO接口
 * @author liuwsh
 * @version 2017-02-17
 */
public class GaDeviceMsDao {
	public static void insert(GaDeviceMs device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("com.thinkgem.ga.entity.GaDeviceMs.insert", device);
		session.commit();
	}
	
	public static void update(GaDeviceMs device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("com.thinkgem.ga.entity.GaDeviceMs.update", device);
		session.commit();
	}
	
	public static GaDeviceMs select(GaDeviceMs device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		GaDeviceMs i = session.selectOne("com.thinkgem.ga.entity.GaDeviceMs.select", device);
		
		return i;
	}
	
}