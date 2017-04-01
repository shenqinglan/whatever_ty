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

import com.thinkgem.ga.entity.GaDeviceAp;




/**
 * 基站DAO接口
 * @author liuwsh
 * @version 2017-02-17
 */
public class GaDeviceApDao {
	public static void insert(GaDeviceAp device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("com.thinkgem.ga.entity.GaDeviceAp.insert", device);
		session.commit();
	}
	
	public static void update(GaDeviceAp device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("com.thinkgem.ga.entity.GaDeviceAp.update", device);
		session.commit();
	}
	
	public static void updateToFail(GaDeviceAp device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("com.thinkgem.ga.entity.GaDeviceAp.updateToFail", device);
		session.commit();
	}
	
	public static GaDeviceAp select(GaDeviceAp device) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		GaDeviceAp i = session.selectOne("com.thinkgem.ga.entity.GaDeviceAp.select", device);
		
		return i;
	}
	
}