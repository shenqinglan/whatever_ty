package com.whty.ga.service;

import com.whty.ga.pojo.GaCardInfo;

public interface IGaIssueCardService {

	GaCardInfo findCardInfoByCardNo(String cardNo);

	void assignGaIssueCardInfo(GaCardInfo card);

	void assignGaIssueCardInfo(GaCardInfo card, GaCardInfo data);

	void saveCardInfo(GaCardInfo card);

}
