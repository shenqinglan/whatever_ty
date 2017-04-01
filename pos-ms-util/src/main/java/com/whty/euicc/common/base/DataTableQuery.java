package com.whty.euicc.common.base;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class DataTableQuery implements Serializable{

	private static final long serialVersionUID = -6509914172864875501L;

	//
	private HttpServletRequest request;
	//起始页数
	private int pageStart; //request.getParameter("start");
	//分页大小
	private int pageLength; //request.getParameter("length");
	//排序列
	private String orderColumn ;//= request.getParameter("order[0][column]"); //2
	//排序方式
	private String orderBy ;//= request.getParameter("order[0][column]"); //2
	//进行排序的列名
	private String orderParam ;//= request.getParameter("columns["+orderColumn+"][data]");
	//draw参数
	private String pageDraw;// request.getParameter("draw");

	private boolean isOrdered;

	//@Value("${isMysql}")
	private String isMysql="true";

	public DataTableQuery( HttpServletRequest request ) {
		this.request = request;
		this.orderColumn = request.getParameter("order[0][column]"); //2
		this.pageStart = Integer.parseInt(request.getParameter("start"));
		this.pageLength = Integer.parseInt(request.getParameter("length"));
		this.orderBy = request.getParameter("order[0][dir]");
		this.orderParam = request.getParameter("columns["+orderColumn+"][name]");
		this.pageDraw = request.getParameter("draw");
	}

	public boolean getIsOrdered() {
		if(orderParam !=null && !"".equals(orderParam)){
			return true;
		}else{
			return false;
		}
	}

	public int getPageStart() {
		return pageStart;
	}


	public int getPageLength() {
		return pageLength;
	}


	public String getOrderBy() {
		return orderBy;
	}


	public String getOrderParam() {
		if("true".equals(isMysql)){
			if(orderParam !=null && !"".equals(orderParam)){
				return "convert( "+orderParam+"  USING gbk) COLLATE gbk_chinese_ci";
			}else{
				return orderParam;
			}
		}else{
			return orderParam;
		}
		
	}


	public String getPageDraw() {
		return pageDraw;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Fetch DataTables Params From Request.pageStart:[").append(pageStart)
				.append("] pageLength:[").append(pageLength)
				.append("] pageDraw:[").append(pageDraw)
				.append("] isOrdered:[").append(isOrdered)
				.append("]");
		if(isOrdered){
			sb.append("orderColumn:[").append(orderColumn)
				.append("] orderParam:[").append("convert(").append(orderParam).append("  USING gbk) COLLATE gbk_chinese_ci")
				.append("] orderBy:[").append(orderBy).append("]");
		}
		return sb.toString();
	}

}
