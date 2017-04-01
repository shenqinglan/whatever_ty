package com.whty.ga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whty.ga.pojo.GaCardInfo;

/**
 * @ClassName GaIssueCardRepo
 * @author Administrator
 * @date 2017-3-3 上午9:52:19
 * @Description TODO(卡片持久层类)
 */
@Repository
public interface GaIssueCardRepo extends JpaRepository<GaCardInfo, String> {

	GaCardInfo findByCardNo(String cardNo);

}
