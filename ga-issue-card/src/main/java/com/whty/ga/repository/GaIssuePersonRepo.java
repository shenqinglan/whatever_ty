package com.whty.ga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whty.ga.pojo.GaPersonInfo;

/**
 * @ClassName GaIssuePersonRepo
 * @author Administrator
 * @date 2017-3-3 上午9:53:04
 * @Description TODO(个人基本信息持久层类)
 */
@Repository
public interface GaIssuePersonRepo extends JpaRepository<GaPersonInfo, String> {

	GaPersonInfo findByIdCardNo(String idCardNo);

	GaPersonInfo findByName(String name);

	GaPersonInfo findByNameAndMobil(String name, String mobil);
}
