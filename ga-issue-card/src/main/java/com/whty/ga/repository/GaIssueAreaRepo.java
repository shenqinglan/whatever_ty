package com.whty.ga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whty.ga.pojo.GaAreaInfo;

/**
 * @ClassName GaIssueAreaRepo
 * @author Administrator
 * @date 2017-3-3 上午9:51:56
 * @Description TODO(小区持久层类)
 */
@Repository
public interface GaIssueAreaRepo extends JpaRepository<GaAreaInfo, String> {

	GaAreaInfo findByAreaName(String areaName);

}
