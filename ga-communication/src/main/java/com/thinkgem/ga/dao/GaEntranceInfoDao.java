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

import com.thinkgem.ga.entity.GaEntranceInfo;



/**
 * 出入信息DAO接口
 * @author liuwsh
 * @version 2017-02-17
 */
public class GaEntranceInfoDao {
	public static void save(GaEntranceInfo entranceInfo) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("com.thinkgem.ga.entity.GaEntranceInfo.insert", entranceInfo);
		session.commit();
	}
	
	public static void main(String args[]) {
		GaEntranceInfo entranceInfo = new GaEntranceInfo();
		entranceInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		entranceInfo.setDoorNo("9be478d926ac40018c3ddd7c6f203c19");
		entranceInfo.setCardNo("f6749fbfc1f0424988758b508ee64f93");
		entranceInfo.setSwipeTime(new Date());
		try {
			save(entranceInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}