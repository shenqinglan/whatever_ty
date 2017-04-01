package com.whty.ga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whty.ga.pojo.GaGateInfo;

/**
 * @ClassName GaIssueGateRepo
 * @author Administrator
 * @date 2017-3-3 上午9:52:32
 * @Description TODO(门禁持久层类)
 */
@Repository
public interface GaIssueGateRepo extends JpaRepository<GaGateInfo, String>{

	@Query("select gate from GaGateInfo gate join gate.area area where area.areaName=?1 and gate.buildingNo=?2 and gate.unitNo=?3")
	GaGateInfo findBy3Elements(String areaName, String buildingNo, String unitNo);
}
