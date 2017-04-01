package com.whty.ga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whty.ga.pojo.GaHouseInfo;

/**
 * @ClassName GaIssueHouseRepo
 * @author Administrator
 * @date 2017-3-3 上午9:52:50
 * @Description TODO(房屋持久层类)
 */
@Repository
public interface GaIssueHouseRepo extends JpaRepository<GaHouseInfo, String> {

	GaHouseInfo findByBuildingNoAndUnitNoAndRoomNo(String buildingNo,
			String unitNo, String roomNo);

}
