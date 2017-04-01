package com.whty.rsp_lpa_app.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.whty.rsp_lpa_app.R;

/**
 * 提示信息的管理
 */

public class PromptManager {
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context, String msg) {
		dialog = new ProgressDialog(context);
		//dialog.setIcon(R.drawable.app_icon);
		//dialog.setTitle(R.string.app_name);

		dialog.setMessage(msg);
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.app_icon)//
				.setTitle(R.string.app_name)//
				.setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到系统的网络设置界面
						Intent intent = new Intent();
						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
						context.startActivity(intent);

					}
				}).setNegativeButton("知道了", null).show();
	}
	
	/**
	 * 显示错误提示框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context)
				.setIcon(R.drawable.app_icon)
				.setTitle(R.string.app_name)
				.setMessage(msg)
				.setNegativeButton("确定", null)
				.show();
	}

	/**
	 * 退出系统
	 * 
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.app_icon)//
				.setTitle(R.string.app_name)//
				.setMessage("是否退出应用").setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//android.os.Process.killProcess(android.os.Process.myPid());
						ExitApplication.getInstance().exit();
					}
				})
				.setNegativeButton("取消", null)
				.show();

	}
}
