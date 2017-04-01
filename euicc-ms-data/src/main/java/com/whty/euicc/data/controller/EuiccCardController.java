package com.whty.euicc.data.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.common.utils.UUIDUtil;
import com.whty.euicc.data.pojo.CapabilitiesInfo;
import com.whty.euicc.data.pojo.CardInfo;
import com.whty.euicc.data.pojo.EcasdInfo;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccCardExample;
import com.whty.euicc.data.pojo.EuiccCardExcelInfo;
import com.whty.euicc.data.pojo.IsdPInfo;
import com.whty.euicc.data.pojo.IsdRInfo;
import com.whty.euicc.data.pojo.Scp03Info;
import com.whty.euicc.data.pojo.Scp80Info;
import com.whty.euicc.data.pojo.Scp81Info;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.thread.importThread;

/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/euiccCard")
public class EuiccCardController extends BaseController {

	@Autowired
	EuiccCardService euiccCardService;

	@Autowired
	private BaseLogsService baseLogsService;
	
	
	
//	private String ecasdId;
	
	/**
	 * 显示主列表页面
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/euiccCard/euiccCardUI";
	}

	/**
	 * 查询
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request, HttpServletResponse response,
			EuiccCard euiccCard) throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String, Object> result = buildTableData(dt, euiccCard);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_EN);
	}

	/**
	 * 构建数据树
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> buildTableData(DataTableQuery dt,
			EuiccCard euiccCard) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		EuiccCardExample example = buildExample(dt, euiccCard);
		PageList<EuiccCard> euiccCards = euiccCardService.selectByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", euiccCards);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", euiccCards.getPaginator().getTotalCount());
		records.put("recordsFiltered", euiccCards.getPaginator()
				.getTotalCount());
		return records;
	}

	/**
	 * 构建列表查询条件
	 * 
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param
	 * @return
	 */
	private EuiccCardExample buildExample(DataTableQuery dt, EuiccCard euiccCard) {
		EuiccCardExample euiccCardExample = new EuiccCardExample();
		EuiccCardExample.Criteria criteria = euiccCardExample.createCriteria();
		// 查询条件
		if (CheckEmpty.isNotEmpty(euiccCard.getEid())) {
			criteria.andEidLike("%" + euiccCard.getEid() + "%");
		}
		// // 排序条件
		// if (CheckEmpty.isNotEmpty(dt.getOrderBy()) &&
		// CheckEmpty.isNotEmpty(dt.getOrderParam())){
		// //
		// StringBuilder orderByClause = new StringBuilder("");
		// orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
		// euiccCardExample.setOrderByClause(orderByClause.toString());
		// }
		return euiccCardExample;
	}
	

	/**
	 * 通过主键查询
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public void view(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		EuiccCard euiccCard = euiccCardService.selectByPrimaryKey(eid);
		writeJSONResult(euiccCard, response);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(EuiccCard euiccCard, HttpServletResponse response,
			String tag) {
		Subject subjuct = SecurityUtils.getSubject();
		BaseUsers currentUser = (BaseUsers) subjuct.getSession().getAttribute(
				Constant.BaseUsersConstant.CURRENTUSER);

		CardInfo cardInfo = euiccCardService.getCardInfo(euiccCard);
		String eid = cardInfo.getEid();
		String ecasdId = cardInfo.getEcasdId();
		System.out.println("ecasdid:" + ecasdId);
		CapabilitiesInfo capabilitiesInfo = euiccCardService.getCapabilitiesInfo(euiccCard);
		IsdRInfo isdRInfo = euiccCardService.getIsdRInfo(euiccCard,tag);
		EcasdInfo ecasdInfo = euiccCardService.getEcasdInfo(euiccCard,ecasdId);
		
		boolean flag = true;
		try {
			if (Constant.SAVESTATUSADD.equals(tag)) {
				euiccCardService.addEuicc(cardInfo, capabilitiesInfo, isdRInfo, ecasdInfo);
			} else if (Constant.SAVESTATUSUPDATE.equals(tag)) {
				euiccCardService.editEuicc(cardInfo, eid, capabilitiesInfo, isdRInfo, ecasdInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		BaseResponseDto baseResponseDto = flag ? new BaseResponseDto(true, "编辑成功")
				: new BaseResponseDto(false, "编辑失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(
				 currentUser, "0", Constant.EUICC_BASE_TYPE,
				 "EUICC_CARD", getObjectData(euiccCardService.selectByPrimaryKey(euiccCard.getEid())),
				 "", "eUICC注册","编辑"));
		writeJSONResult(baseResponseDto, response);
	}

	

	/**
	 * 删除菜单信息
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request,
			HttpServletResponse response, String eid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccCard euiccCard = euiccCardService.selectByPrimaryKey(eid);
		String ecasdId = euiccCardService.selectByPrimaryKey(eid).getEcasdId();
		// 删除
		
		boolean flag = true;
		try {
			euiccCardService.deleteEuicc(eid, ecasdId);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		BaseResponseDto baseResponseDto = flag ? new BaseResponseDto(true, "删除成功")
				: new BaseResponseDto(false, "删除失败");
		 baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
		 "2", Constant.EUICC_BASE_TYPE, "AC_INFO",
		 getObjectData(euiccCard), "", "eUICC注册", "删除"));
		writeJSONResult(baseResponseDto, response);
	}
	
	
	/**
	 * Log数据
	 * @param euiccCard
	 * @return
	 */
	private String getObjectData(EuiccCard euiccCard) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("EuiccCard[");
		sBuffer.append("eid=");
		sBuffer.append(euiccCard.getEid());
		sBuffer.append(", ");
		sBuffer.append("eumId=");
		sBuffer.append(euiccCard.getEumId());
		sBuffer.append("]");
		return sBuffer.toString();
	}
	
	/**
	 * check上传的excel
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/importExl", method = RequestMethod.POST)
	public void importExl(@RequestParam("fileUpload") CommonsMultipartFile file, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!file.getOriginalFilename().contains(".xls")) {
			writeJSONResult(new BaseResponseDto(true,"请选择Excel文档"), response);
			return;
		}
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
        DiskFileItem fi = (DiskFileItem)file.getFileItem(); 

        File f = fi.getStoreLocation(); 

        InputStream is = new FileInputStream(f);
        try {
			Workbook workbook  = Workbook.getWorkbook(is);
			if (workbook.getSheets().length != 5) {
				writeJSONResult(new BaseResponseDto(false,"Excel模板不满足要求，应包含5个sheet页"), response);
				return;
			}
			EuiccCardExcelInfo info = getExcelInfo(workbook);
			if (info.getEuiccCardList() == null || info.getEuiccCardList().size() == 0) {
				writeJSONResult(new BaseResponseDto(false,"卡基本数据为空"), response);
				return;
			}
			
			ExecutorService threadPool = Executors.newSingleThreadExecutor();
			Future<String> future = threadPool.submit(new importThread(euiccCardService, currentUser, info));
			String respResult = future.get();
			System.out.println("respResult:" + respResult);
			
			baseLogsService.insertSelective(LogTables.getOperateLog(
			currentUser, "0", Constant.EUICC_BASE_TYPE,
			"BASE_FIELDS", "", "", "eUICC注册",
			"导入excel"));
			writeJSONResult(new BaseResponseDto(true,"导入成功"), response);
		}catch (BiffException e) {
			e.printStackTrace();
			writeJSONResult(new BaseResponseDto(false,"读取Excel失败"), response);
		}catch (Exception e) {
			e.printStackTrace();
			writeJSONResult(new BaseResponseDto(false,"导入失败"), response);
		}
		
	}

	

	/**
	 * 解析excel数据
	 * @param workbook
	 * @return
	 */
	private EuiccCardExcelInfo getExcelInfo(Workbook workbook) {
		
		EuiccCardExcelInfo euiccCardExcelInfo = new EuiccCardExcelInfo();
		List<EuiccCard> euiccCardList = new ArrayList<EuiccCard>();
		List<Scp80Info> scp80InfoList = new ArrayList<Scp80Info>();
		List<Scp81Info> scp81InfoList = new ArrayList<Scp81Info>();
		List<Scp03Info> scp03InfoList = new ArrayList<Scp03Info>();
		List<IsdPInfo> isdPInfoList = new ArrayList<IsdPInfo>();
		
		//解析卡基本信息sheet
		Sheet sheet1 = workbook.getSheet(0);
		for (int i = 1; i < sheet1.getRows(); i++) {
			EuiccCard euiccCard = new EuiccCard();
			Cell[] cells = sheet1.getRow(i);
			euiccCard.setEid(cells[0].getContents());
			euiccCard.setEumId(cells[1].getContents());
			euiccCard.setProductiondate(cells[2].getContents());
			euiccCard.setPlatformtype(cells[3].getContents());
			euiccCard.setPlatformversion(cells[4].getContents());
			if (cells[5].getContents() != null && !"".equals(cells[5].getContents())) {
				euiccCard.setRemainingmemory(Integer.valueOf(cells[5].getContents()));
			}
			if (cells[6].getContents() != null && !"".equals(cells[6].getContents())) {
				euiccCard.setAvailablememoryforprofiles(Integer.valueOf(cells[6].getContents()));
			}
			euiccCard.setSmsrId(cells[7].getContents());
			euiccCard.setEcasdId(cells[8].getContents());
			euiccCard.setCapabilityId(cells[9].getContents());
			euiccCard.setCatTpSupport(cells[10].getContents());
			euiccCard.setCatTpVersion(cells[11].getContents());
			euiccCard.setHttpSupport(cells[12].getContents());
			euiccCard.setHttpVersion(cells[13].getContents());
			euiccCard.setSecurePacketVersion(cells[14].getContents());
			euiccCard.setRemoteProvisioningVersion(cells[15].getContents());
			euiccCard.setIsdRAid(cells[16].getContents());
			euiccCard.setPol1Id(cells[17].getContents());
			euiccCard.setCertEcasdEcka(cells[18].getContents());
			euiccCard.setPhoneNo(cells[19].getContents());
			euiccCardList.add(euiccCard);
		}
		
		//解析Scp80
		Sheet sheet2 = workbook.getSheet(1);
		for (int i = 1; i < sheet2.getRows(); i++) {
			Scp80Info scp80Info = new Scp80Info();
			Cell[] cells = sheet2.getRow(i);
			scp80Info.setScp80Id(UUIDUtil.getUuidString());
			scp80Info.setEid(cells[0].getContents());
			scp80Info.setId(cells[1].getContents());
			scp80Info.setVersion(cells[2].getContents());
			scp80Info.setData(cells[3].getContents());
			scp80InfoList.add(scp80Info);
		}
		
		//解析Scp81
		Sheet sheet3 = workbook.getSheet(2);
		for (int i = 1; i < sheet3.getRows(); i++) {
			Scp81Info scp81Info = new Scp81Info();
			Cell[] cells = sheet3.getRow(i);
			scp81Info.setScp81Id(UUIDUtil.getUuidString());
			scp81Info.setEid(cells[0].getContents());
			scp81Info.setId(cells[1].getContents());
			scp81Info.setVersion(cells[2].getContents());
			scp81Info.setData(cells[3].getContents());
			scp81InfoList.add(scp81Info);
		}
		
		//解析Scp03
		Sheet sheet4 = workbook.getSheet(3);
		for (int i = 1; i < sheet4.getRows(); i++) {
			Scp03Info scp03Info = new Scp03Info();
			Cell[] cells = sheet4.getRow(i);
			scp03Info.setScp03Id(UUIDUtil.getUuidString());
			scp03Info.setEid(cells[0].getContents());
			scp03Info.setIsdPAid(cells[1].getContents());
			scp03Info.setId(cells[2].getContents());
			scp03Info.setVersion(cells[3].getContents());
			scp03Info.setData(cells[4].getContents());
			scp03InfoList.add(scp03Info);
		}
		
		//解析ISDP
		Sheet sheet5 = workbook.getSheet(4);
		for (int i = 1; i < sheet5.getRows(); i++) {
			Date date = new Date();
			IsdPInfo isdPInfo = new IsdPInfo();
			Cell[] cells = sheet5.getRow(i);
			isdPInfo.setpId(UUIDUtil.getUuidString());
			isdPInfo.setEid(cells[0].getContents());
			isdPInfo.setIsdPAid(cells[1].getContents());
			isdPInfo.setIsdPState(cells[2].getContents());
			isdPInfo.setIsdPLoadfileAid(cells[3].getContents());
			isdPInfo.setIsdPModuleAid(cells[4].getContents());
			isdPInfo.setConnectivityParam(cells[5].getContents());
			isdPInfo.setCreateDt(date);
			isdPInfo.setUpdateDt(date);
			isdPInfoList.add(isdPInfo);
		}
		
		euiccCardExcelInfo.setEuiccCardList(euiccCardList);
		euiccCardExcelInfo.setScp80InfoList(scp80InfoList);
		euiccCardExcelInfo.setScp81InfoList(scp81InfoList);
		euiccCardExcelInfo.setScp03InfoList(scp03InfoList);
		euiccCardExcelInfo.setIsdPInfoList(isdPInfoList);
		return euiccCardExcelInfo;
	}
}
