package com.whty.euicc.base.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.dto.BaseLogsCondDto;
import com.whty.euicc.base.dto.LogsUser;
import com.whty.euicc.base.pojo.BaseLogsExample;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.pojo.BaseUsersExample;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.base.service.BaseUsersService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.excel.ExportExcel;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @author dzmsoft
 * @date 2015-07-14 19:25
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/baseLogs")
public class BaseLogsController extends BaseController{

	@Autowired
	BaseLogsService baseLogsService;

	@Autowired
	BaseUsersService baseUsersService;

	@Value("${isEncryptLog}")
	private String isEncryptLog;

	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2015-07-14 19:25
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView  mv = new ModelAndView("modules/base/baseLogs/baseLogsUI");
		//所有用户账号
		PageList<BaseUsers> baseUsersList = this.baseUsersService.selectByExample(new BaseUsersExample(), new PageBounds());
		mv.addObject("userAccount", baseUsersList);
		return mv;
	}

	/**
	 * 查询
	 * @dzmsoftgenerated 2015-07-14 19:25
	 * @return
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,HttpServletResponse response, BaseLogsCondDto baseLogsCondDto) {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, baseLogsCondDto);
		writeJSONResult(result, response,DateUtil.yyyy_MM_dd_HH_mm_EN);
	}

	/**
	 * 构建数据树
	 * @dzmsoftgenerated 2015-07-14 19:25
	 * @param dt
	 * @param baseLogsCondDto
	 * @return
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt,BaseLogsCondDto baseLogsCondDto) {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		BaseLogsExample example = buildExample(dt, baseLogsCondDto);
		PageList<LogsUser> baseLogss =baseLogsService.selectSystemLog(example, pageBounds);
		LogTables.decryptLogs(baseLogss);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", baseLogss);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", baseLogss.getPaginator().getTotalCount());
		records.put("recordsFiltered", baseLogss.getPaginator().getTotalCount());
		return records;
	}


	/**
	 *
	 */
	@RequestMapping(value = "/export")
	public void export(int pageLength,int page,BaseLogsCondDto baseLogsCondDto,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PageBounds pageBounds = new PageBounds(page, pageLength);
		//BaseLogsExample example = buildExample(dt, baseLogsCondDto);
		BaseLogsExample example =buildExampleExport(baseLogsCondDto);
		PageList<LogsUser> baseLogss =baseLogsService.selectSystemLog(example, pageBounds);
		LogTables.decryptLogs(baseLogss);

		String fileName = "导出日志.xls";
		fileName = new String(fileName.getBytes("GBK"), "iso8859-1");
		response.reset();
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);// 指定下载的文件名
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream output = response.getOutputStream();
		BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);

		// 定义单元格报头
		String worksheetTitle = "导出日志信息";

		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建单元格样式
		HSSFCellStyle cellStyleTitle = wb.createCellStyle();
		// 指定单元格居中对齐
		cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 指定单元格垂直居中对齐
		cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 指定当单元格内容显示不下时自动换行
		cellStyleTitle.setWrapText(true);
		// ------------------------------------------------------------------
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// 指定单元格居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 指定单元格垂直居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 指定当单元格内容显示不下时自动换行
		cellStyle.setWrapText(false);
		// ------------------------------------------------------------------
		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyleTitle.setFont(font);

		// 工作表名
		String opUser = "操作员";
		String op = "操作";
		String date = "操作日期";
		String mark = "备注";


		HSSFSheet sheet = wb.createSheet();
		//设置表格宽度,第一个参数表示列数，从0开始；第二个参数单位是1/256个字符宽度
		sheet.setColumnWidth(0, 10*256);
		sheet.setColumnWidth(1, 10*256);
		sheet.setColumnWidth(2, 20*256);
		sheet.setColumnWidth(3, 100*256);
		ExportExcel exportExcel = new ExportExcel(wb, sheet);
		// 创建报表头部
		exportExcel.createNormalHead(worksheetTitle, 4);
		// 定义第一行
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell1 = row1.createCell(0);

		//第一行第一列

		cell1.setCellStyle(cellStyleTitle);
		cell1.setCellValue(new HSSFRichTextString(opUser));
		//第一行第er列
		cell1 = row1.createCell(1);
		cell1.setCellStyle(cellStyleTitle);
		cell1.setCellValue(new HSSFRichTextString(op));

		//第一行第san列
		cell1 = row1.createCell(2);
		cell1.setCellStyle(cellStyleTitle);
		cell1.setCellValue(new HSSFRichTextString(date));

		//第一行第si列
		cell1 = row1.createCell(3);
		cell1.setCellStyle(cellStyleTitle);
		cell1.setCellValue(new HSSFRichTextString(mark));


		//定义第二行
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(1);
		LogsUser logsUser = new LogsUser();
		for (int i = 0; i < baseLogss.size(); i++) {
			logsUser = baseLogss.get(i);
			row = sheet.createRow(i + 2);

			cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(logsUser.getAccount() + ""));

			cell = row.createCell(1);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(logsUser.getOpType()));

			cell = row.createCell(2);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(logsUser.getOpDate() + ""));

			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new HSSFRichTextString(logsUser.getRemark() + ""));

		}
		try {
			bufferedOutPut.flush();
			wb.write(bufferedOutPut);
			bufferedOutPut.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output   is   closed ");
		} finally {
			baseLogss.clear();
		}
	}

	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2015-07-14 19:25
	 * @param
	 * @return
	 */
	private BaseLogsExample buildExample(DataTableQuery dt,BaseLogsCondDto baseLogsCondDto) {
		BaseLogsExample baseLogsExample = new BaseLogsExample();
		BaseLogsExample.Criteria criteria = baseLogsExample.createCriteria();
		String startTime = baseLogsCondDto.getDayStart();
		String endTime =  baseLogsCondDto.getDayEnd();
		if (CheckEmpty.isNotEmpty(startTime)) {
			startTime=startTime+" 00:00:00";
			criteria.andOpDateGreaterThan(DateUtil.getDate(startTime, DateUtil.yyyy_MM_dd_HH_mm_ss_EN));
		}
		if (CheckEmpty.isNotEmpty(endTime)) {
			endTime=endTime+"  23:59:59";
			criteria.andOpDateLessThan(DateUtil.getDate(endTime, DateUtil.yyyy_MM_dd_HH_mm_ss_EN));
		}

		if (CheckEmpty.isNotEmpty( baseLogsCondDto.getUserId())) {
			criteria.andUserIdLike("%"+baseLogsCondDto.getUserId()+"%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			//
			StringBuilder orderByClause = new StringBuilder(" ");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			baseLogsExample.setOrderByClause(orderByClause.toString());
		}
		return baseLogsExample;
	}

	private BaseLogsExample buildExampleExport(BaseLogsCondDto baseLogsCondDto) {
		BaseLogsExample baseLogsExample = new BaseLogsExample();
		BaseLogsExample.Criteria criteria = baseLogsExample.createCriteria();
		String startTime = baseLogsCondDto.getDayStart();
		String endTime =  baseLogsCondDto.getDayEnd();
		if (CheckEmpty.isNotEmpty(startTime)) {
			startTime=startTime+" 00:00:00";
			criteria.andOpDateGreaterThan(DateUtil.getDate(startTime, DateUtil.yyyy_MM_dd_HH_mm_ss_EN));
		}
		if (CheckEmpty.isNotEmpty(endTime)) {
			endTime=endTime+"  23:59:59";
			criteria.andOpDateLessThan(DateUtil.getDate(endTime, DateUtil.yyyy_MM_dd_HH_mm_ss_EN));
		}

		if (CheckEmpty.isNotEmpty( baseLogsCondDto.getUserId())) {
			criteria.andUserIdLike("%"+baseLogsCondDto.getUserId()+"%");
		}
		// 排序条件
		baseLogsExample.setOrderByClause("OP_DATE DESC");

		return baseLogsExample;
	}
}
