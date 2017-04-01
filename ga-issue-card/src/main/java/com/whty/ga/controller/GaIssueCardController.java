package com.whty.ga.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.whty.ga.pojo.GaAreaInfo;
import com.whty.ga.pojo.GaCardInfo;
import com.whty.ga.pojo.GaGateInfo;
import com.whty.ga.pojo.GaHouseInfo;
import com.whty.ga.pojo.GaPersonInfo;
import com.whty.ga.service.IGaIssueAreaService;
import com.whty.ga.service.IGaIssueCardService;
import com.whty.ga.service.IGaIssueGateService;
import com.whty.ga.service.IGaIssueHouseService;
import com.whty.ga.service.IGaIssuePersonService;
import com.whty.ga.util.StringUtil;

/**
 * @ClassName GaIssueCardController
 * @author Administrator
 * @date 2017-3-3 上午9:50:15
 * @Description TODO(发卡系统controller类，处理http请求并返回)
 */
@RestController
@RequestMapping("/ga-issue-card")
public class GaIssueCardController {

	@Autowired
	@Qualifier("gaIssueCardServiceImpl")
	private IGaIssueCardService cardService;
	
	
	@Autowired
	@Qualifier("gaIssueAreaServiceImpl")
	private IGaIssueAreaService areaService;
	
	@Autowired
	@Qualifier("gaIssueHouseServiceImpl")
	private IGaIssueHouseService houseService;

	
	@Autowired
	@Qualifier("gaIssuePersonServiceImpl")
	private IGaIssuePersonService personService;
	
	@Autowired
	@Qualifier("gaIssueGateServiceImpl")
	private IGaIssueGateService gateService;
	
	private String msg = null;
	
	/**
	 * 
	 * @param params:{json串}
	 * @return
	 * http://http://localhost:8080/ga-issue-card/v1/ga/upload/saveInfo
	 */
	@RequestMapping(value="/v1/ga/upload/saveInfo",method = RequestMethod.POST)
	public Object saveInfo(@RequestParam(required = false) String params){
		System.out.println("hello >>> "+params);
		JSONObject result = new JSONObject();
		result.put("code", -1);
		result.put("app", "ga-issue-card");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String timeStamp = sdf.format(time);
		// 如果参数为空
		if (StringUtils.isBlank(params)) {
			result.put("time", timeStamp);
			result.put("code",1);
			result.put("msg", "没有数据上传!");
			return result;
		}
		
		// 解析数据进行数据上传
		JSONObject obj = new JSONObject();
		try {
			obj = JSONObject.parseObject(params);
		} catch (Exception e) {
			result.put("time", timeStamp);
			result.put("code",1);
			result.put("msg", "参数请求格式错误!");
			return result;
		}
		GaPersonInfo personInfo = null;
		GaCardInfo cardInfo = null;
		GaHouseInfo houseInfo = null;
		GaAreaInfo areaInfo = null;
		GaGateInfo gateInfo = null;
		for (String flag : obj.keySet()) {
			JSONObject json = obj.getJSONObject(flag);
			if (StringUtils.equalsIgnoreCase("person", flag)) {
				GaPersonInfo person = assignPersonInfo(json);
				if (person == null) {
					result.put("time", timeStamp);
					result.put("msg", msg);
					return result;
				}
				personInfo = processPersonInfo(person);
				
			} 
			if (StringUtils.equalsIgnoreCase("card", flag)) {
				GaCardInfo card = assignCardInfo(json);
				if (card == null) {
					result.put("time", timeStamp);
					result.put("msg", msg);
					return result;
				}
				cardInfo = processCardInfo(card);
			} 
			if (StringUtils.equalsIgnoreCase("house", flag)) {
				JSONObject jsonHouse = StringUtil.trimJSONString(json);
				GaHouseInfo house = assignHouseInfo(jsonHouse);
				GaAreaInfo area = assignAreaInfo(jsonHouse);
				GaGateInfo gate = assignGateInfo(jsonHouse);
				if (house == null || area == null || gate == null) {
					result.put("time", timeStamp);
					result.put("msg", msg);
					return result;
				}
				houseInfo = processHouseInfo(house);
				areaInfo = processAreaInfo(area);
				gateInfo = processGateInfo(gate);
			} 
		}
		
		gateInfo.setArea(areaInfo);
		houseInfo.setArea(areaInfo);
		houseInfo.setGate(gateInfo);
		cardInfo.setPerson(personInfo);
		cardInfo.setHouse(houseInfo);
		
		areaService.saveAreaInfo(areaInfo);
		gateService.saveGateInfo(gateInfo);
		personService.savePersonInfo(personInfo);
		houseService.saveHouseInfo(houseInfo);
		cardService.saveCardInfo(cardInfo);
		
		JSONObject dataResult = new JSONObject();
		dataResult.put("areaName", areaInfo.getAreaName());
		dataResult.put("buildingNo", houseInfo.getBuildingNo());
		dataResult.put("unitNo", houseInfo.getUnitNo());
		dataResult.put("roomNo", houseInfo.getRoomNo());
		dataResult.put("name", personInfo.getName());
		dataResult.put("relation", cardInfo.getRelation());
		result.put("time", timeStamp);
		result.put("data", dataResult);
		result.put("code",0);
		return result;
	}
	
	
	private GaPersonInfo assignPersonInfo(JSONObject json) {
		JSONObject jsonPerson = StringUtil.trimJSONString(json);
		GaPersonInfo person = null;
		try { 
			person = JSONObject.parseObject(jsonPerson.toJSONString(), GaPersonInfo.class);
		} catch (Exception e) {
			msg = "解析持卡人信息出错!";
			return null;
		}
		if (StringUtils.isBlank(person.getIdCardNo())) {
			msg = "持卡人身份证号码为空!";
			return null;
		}
		return person;
	}
	
	private GaCardInfo assignCardInfo(JSONObject json) {
		JSONObject jsonCard = StringUtil.trimJSONString(json);
		GaCardInfo card = null;
		try {
			card = JSONObject.parseObject(jsonCard.toJSONString(), GaCardInfo.class);
		} catch (Exception e) {
			msg = "解析卡片上传信息出错!";
			return null;
		}
		if (StringUtils.isBlank(card.getCardNo())) {
			msg = "卡片编号为空!";
			return null;
		}
		
		return card;
	}
	
	private GaHouseInfo assignHouseInfo(JSONObject jsonHouse) {
		GaHouseInfo house = null;
		try {
			house = JSONObject.parseObject(jsonHouse.toJSONString(), GaHouseInfo.class);
		} catch (Exception e) {
			msg = "解析房屋信息出错!";
			return null;
		}
		if (StringUtils.isBlank(house.getBuildingNo()) || StringUtils.isBlank(house.getUnitNo()) || StringUtils.isBlank(house.getRoomNo())) {
			msg = "房屋信息：楼栋号、单元号、房间号为空";
			return null;
		}
		return house;
		
	}
	
	private GaAreaInfo assignAreaInfo(JSONObject jsonHouse) {
		GaAreaInfo area = new GaAreaInfo();
		String areaName = jsonHouse.getString("areaName");
		if (StringUtils.isBlank(areaName)) {
			msg = "小区名称为空，请重新上传后提交!";
			return null;
		} else {
			area.setAreaName(areaName);
			return area;
		}
	}
	
	private GaGateInfo assignGateInfo(JSONObject jsonHouse) {
		GaGateInfo gate = new GaGateInfo();
		String buildingNo = jsonHouse.getString("buildingNo");
		String unitNo = jsonHouse.getString("unitNo");
		String areaName = jsonHouse.getString("areaName");
		if (StringUtils.isBlank(areaName) || StringUtils.isBlank(buildingNo) || StringUtils.isBlank(unitNo)) {
			msg = "小区名称,楼栋或者单元信息为空，请重新上传后提交!";
			return null;
		} else {
			gate.setBuildingNo(buildingNo);
			gate.setUnitNo(unitNo);
			
			// 查找到小区信息
			GaAreaInfo area = areaService.findAreaInfoByAreaName(areaName);
			if (area == null) {
				GaAreaInfo data = new GaAreaInfo();
				data.setAreaName(areaName);
				areaService.assignGaIssueAreaInfo(data);
				areaService.saveAreaInfo(data);
				gate.setArea(data);
			} else {
				gate.setArea(area);
			}
			return gate;
		}
	}

	private GaPersonInfo processPersonInfo(GaPersonInfo person) {
		
		// 校验数据库是否存在已有的person，如果有就是更新，没有就插入
		GaPersonInfo data = personService.findPersonInfoByIdCardNo(person.getIdCardNo());
		
		//如果data为空，返回person 
		if (data == null) {
			personService.assignGaIssuePersonInfo(person);
			return person;
		} else {
			personService.assignGaIssuePersonInfo(person, data);
			return data;
		}
	}
	
	private GaCardInfo processCardInfo(GaCardInfo card) {
		
		// 校验数据库是否存在已有的card，如果有就是更新，没有就插入
		GaCardInfo data = cardService.findCardInfoByCardNo(card.getCardNo());
		
		//如果data为空，添加到数据库 
		if (data == null) {
			cardService.assignGaIssueCardInfo(card);
			return card;
		} else {
			cardService.assignGaIssueCardInfo(card, data);
			return data;
		}
	}
	
	private GaHouseInfo processHouseInfo(GaHouseInfo house) {
		
		// 校验数据库是否存在已有的house，如果有就是更新，没有就插入
		GaHouseInfo data = houseService.findHouseInfoByBuildingNoAndUnitNoAndRoomNo(house.getBuildingNo(),house.getUnitNo(), house.getRoomNo());
		
		// 如果data为空，返回house
		if (data == null) {
			houseService.assignGaIssueHouseInfo(house);
			return house;
		} else {
			houseService.assignGaIssueHouseInfo(house, data);
			return data;
		}
	}
	
	private GaAreaInfo processAreaInfo(GaAreaInfo area) {
		// 校验数据库是否存在已有的area，如果有就是更新，没有就插入
		GaAreaInfo data = areaService.findAreaInfoByAreaName(area.getAreaName());
		
		// 如果data为空，返回house
		if (data == null) {
			areaService.assignGaIssueAreaInfo(area);
			return area;
		} else {
			areaService.assignGaIssueAreaInfo(area, data);
			return data;
		}
	}
	
	private GaGateInfo processGateInfo(GaGateInfo gate) {
		
		// 校验数据库是否存在已有的gate，如果有就是更新，没有就插入
		GaGateInfo data = gateService.findGateInfoByAreaNameAndBuildingNoAndUnitNo(gate.getArea().getAreaName(),gate.getBuildingNo(),gate.getUnitNo());
		
		// 如果data为空，返回gate
		if (data == null) {
			gateService.assignGaIssueGateInfo(gate);
			return gate;
		} else {
			gateService.assignGaIssueGateInfo(gate, data);
			return data;
		}
	}
}
