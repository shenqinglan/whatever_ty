/**
 * @Title: package-info.java
 * @Package com.whty.tsm.business.task
 *
 * @author baojw@whty.com.cn
 * @date 2014年10月20日 下午2:12:39
 * @version V1.0
 */

/**
 * 所有原子任务
 * 原子任务分三类
 * 1、安全域管理
 * @see com.whty.tsm.business.task.secureDomain
 * 1.1、主安全域管理
 * @see com.whty.tsm.business.task.secureDomain.issuer
 * 1.2、辅助安全域管理
 * @see com.whty.tsm.business.task.secureDomain.sub
 * 2、应用管理任务
 * @see com.whty.tsm.business.task.appManager
 * 3、卡操作管理 
 * @see com.whty.tsm.business.task.cardOperator
 * 
 * 
 * 
 * 主安全域的锁定、解锁、废止
 */
package com.whty.euicc.tasks.basetask;