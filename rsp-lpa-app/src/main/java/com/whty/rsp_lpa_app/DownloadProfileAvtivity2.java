package com.whty.rsp_lpa_app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whty.euicc.rsp.oma.CardMain;
import com.whty.euicc.rsp.packets.message.response.AuthenticateClientResp;
import com.whty.euicc.rsp.packets.message.response.GetBoundProfilePackageResp;
import com.whty.euicc.rsp.packets.message.response.InitiateAuthenticationResp;
import com.whty.euicc.rsp.profile.ProfileUtil;
import com.whty.rsp_lpa_app.bean.AuthenticateServerResponse;
import com.whty.rsp_lpa_app.bean.PrepareDownloadResponse;
import com.whty.rsp_lpa_app.tls.TlsES9PlusManager;
import com.whty.rsp_lpa_app.utils.BaseActivity;
import com.whty.rsp_lpa_app.utils.GlobalParams;
import com.whty.rsp_lpa_app.utils.PromptManager;

public class DownloadProfileAvtivity2 extends BaseActivity{
	
	private final String TAG = "DownloadProfileAvtivity2";
	private String downloadType;
	private String result;
	private CardMain cardMain;
	private Dialog downloaddialog;
	private Dialog installdialog;
	private TextView description;
	private Gson gson;
	private String transactionId = "1122334455667788";
	private String profileMetadata;
	private String smdpSigned2;
	private String smdpSignature2;
	private String smdpCertificate;
	private String confirmCode;
	private String BPP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		downloadType = getIntent().getStringExtra("download_type");
		result = getIntent().getStringExtra("result");
		cardMain = new CardMain(this, "11111111");
		gson = new Gson();
		initView();
		downloadAndInstall();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		description = (TextView) findViewById(R.id.description);
	}
	
	/**
	 * 下载安装流程
	 */
	private void downloadAndInstall() {
		// 获取smdp地址，euiccinfo1，随机数
		final String smdpAdress = getDpAddress(downloadType, result);
		final String euiccinfo1 = getEuiccInfo();
		final String eUICCChallenge = getEUICCChallenge();
		new AsyncTask<String, Integer, String>() {
			
			@Override
			protected void onPreExecute() {
				description.setText("双向认证中...");
			}
			
			@Override
			protected String doInBackground(String... params) {
				//调用ES9+.initiateAuthentication
				return TlsES9PlusManager.getInstance().initiateAuthentication(eUICCChallenge, euiccinfo1, smdpAdress, params[0], Integer.parseInt(params[1]));
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				authenticateClient(result);
			}
		}.execute(GlobalParams.DP_IP_PORT);

	}
	
	/**
	 * 调用ES9+.authenticateClient
	 * @param result
	 */
	private void authenticateClient(String result) {
		InitiateAuthenticationResp initiateAuthenticationResp = gson.fromJson(result, InitiateAuthenticationResp.class);
//		if (error) {
//			PromptManager.showErrorDialog(this, msg);
//			return;
//		}
		//验证dp地址
		if (!checkDpAddress()) {
			PromptManager.showErrorDialog(this, "SM-DP+地址不匹配");
			return;
		}
		//生成上下文参数
		String ctxParams1 = generateCtxParams1();
		//调用ES10b.authenticateServer
		final AuthenticateServerResponse authenticateServerResponse = authenticateServer(initiateAuthenticationResp.getServerSigned1(), 
															   initiateAuthenticationResp.getServerSignature1(), 
															   initiateAuthenticationResp.getEuiccCiPKIdTobeUsed(), 
															   initiateAuthenticationResp.getServerCertificate(), 
															   ctxParams1);
		
		new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... params) {
				//调用ES9+.authenticateClient
				return TlsES9PlusManager.getInstance().authenticateClient(transactionId, 
						authenticateServerResponse.getEuiccSigned1(),
						authenticateServerResponse.getEuiccSignature1(), 
						authenticateServerResponse.getEuiccCertificate(),
						authenticateServerResponse.getEumCertificate(),
						params[0], Integer.parseInt(params[1]));
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				checkProfile(result);
				Toast.makeText(DownloadProfileAvtivity2.this, "双向认证成功", 0).show();
				description.setText("用户确认中...");
			}
		}.execute(GlobalParams.DP_IP_PORT);
		
	}
	
	/**
	 * 检查profile
	 * @param result
	 */
	private void checkProfile(String result) {
		AuthenticateClientResp authenticateClientResp = gson.fromJson(result, AuthenticateClientResp.class);
		profileMetadata = authenticateClientResp.getProfileMetadata();
		smdpSigned2 = authenticateClientResp.getSmdpSigned2();
		smdpSignature2 = authenticateClientResp.getSmdpSignature2();
		smdpCertificate = authenticateClientResp.getSmdpCertificate();
//		if (error) {
//			PromptManager.showErrorDialog(this, msg);
//			return;
//		}
		//检查是否包含PPR
		if(isContainPPR()) {
			//getRAT();
			//getProfilesInfo();
			if (isRATAllowedPPR()) {
				AlertDialog.Builder builder = new Builder(this);
				builder.setIcon(R.drawable.app_icon)
					   .setTitle(R.string.app_name)
					   .setMessage("是否同意此PPR").setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							confirmationCodeDialog();
						}
					}).setNegativeButton("取消", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								userRejection(transactionId, "End User rejection");
							}
						}).show();
			} else {
				userRejection(transactionId, "PPR not allowed");
			}
		} else {
			confirmationCodeDialog();
		}
	}
	
	/**
	 * 检查确认码
	 */
	private void confirmationCodeDialog() {
		//检查是否有确认码
		final EditText code;
		if (isConfirmationCodeRequired()) {
			View view = View.inflate(this, R.layout.confirmation_dailog, null);
			code = (EditText) view.findViewById(R.id.confirm_code);
			AlertDialog.Builder builder = new Builder(this);
			final AlertDialog dialog = builder.setIcon(R.drawable.app_icon)
		   	   .setTitle(R.string.app_name)
		   	   .setView(view)
		   	   .setPositiveButton("确定", null)
		   	   .setNegativeButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
			}).setCancelable(false).show();
			
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					confirmCode = code.getText().toString().trim();
					if (null == confirmCode || "".equals(confirmCode)) {
						Toast.makeText(getApplicationContext(), "确认码不能为空", 1).show();
					} else {
						dialog.dismiss();
						downloadProfile();
					}
				}
			});
		} else {
			downloadProfile();
		}
	}
	
	/**
	 * 下载流程
	 * @param confirmationCode
	 */
	private void downloadProfile() {
		//用户确认是否下载Profile
		//设置定时器
		final Timer downloadTimer = new Timer();
		TimerTask downloadTask = new TimerTask() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if (downloaddialog != null && downloaddialog.isShowing()) {
							downloaddialog.dismiss();
							downloadTimer.cancel();
							userRejection(transactionId, "Timeout");
						}
					}
				});
				
			}
		};
		downloadTimer.schedule(downloadTask, 30000);
		AlertDialog.Builder builder = new Builder(this);
		downloaddialog = builder.setIcon(R.drawable.app_icon)
			   	   .setTitle(R.string.app_name)
			   	   .setMessage("是否确定下载此Profile")
		   	   .setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					downloadTimer.cancel();
					prepareDownload();
				}
			}).setCancelable(false).setNeutralButton("稍后", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					downloadTimer.cancel();
					userRejection(transactionId, "End User postponed");
				}
			}).setNegativeButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					downloadTimer.cancel();
					userRejection(transactionId, "End User rejection");
				}
			}).show();
		
	}
	
	/**
	 * 下载准备
	 */
	private void prepareDownload() {
		//准备下载
		final PrepareDownloadResponse prepareDownloadResponse = prepareDownload(smdpSigned2, smdpSignature2, smdpCertificate, confirmCode);
//		if (error) {
//			PromptManager.showErrorDialog(this, msg);
//			return;
//		}
		new AsyncTask<String, Integer, String>() {
			
			@Override
			protected void onPreExecute() {
				description.setText("下载中...");
			};
			
			@Override
			protected String doInBackground(String... params) {
				//调用ES9+.getBoundProfilePackage
				return TlsES9PlusManager.getInstance().getBoundProfilePackage(transactionId, 
						prepareDownloadResponse.getEuiccSigned2(),
						prepareDownloadResponse.getEuiccSignature2(),
						params[0],
						Integer.parseInt(params[1]));
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				confirmInstall(result);
				Toast.makeText(DownloadProfileAvtivity2.this, "下载成功", 0).show();
			}
		}.execute(GlobalParams.DP_IP_PORT);
	}
	
	/**
	 * 确认安装
	 * @param result
	 */
	private void confirmInstall(String result) {
		GetBoundProfilePackageResp getBoundProfilePackageResp = gson.fromJson(result, GetBoundProfilePackageResp.class);
		BPP = getBoundProfilePackageResp.getBoundProfilePackage();
		//认证metadata
		checkMetadata();
		//显示metadata
		showMetadata();
		//设置定时器
		final Timer installTimer = new Timer();
		TimerTask installTask = new TimerTask() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if (installdialog != null && installdialog.isShowing()) {
							installdialog.dismiss();
							installTimer.cancel();
							userRejection(transactionId, "Timeout");
						}
					}
				});
			}
		};
		installTimer.schedule(installTask, 30000);
		//用户确认是否安装Profile
		AlertDialog.Builder builder = new Builder(this);
		installdialog = builder.setIcon(R.drawable.app_icon)
			   	   .setTitle(R.string.app_name)
			   	   .setMessage("是否确定安装此Profile")
		   	   .setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					installTimer.cancel();
					installProfile();
				}
			}).setCancelable(false).setNeutralButton("稍后", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					installTimer.cancel();
					userRejection(transactionId, "End User postponed");
				}
			}).setNegativeButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					installTimer.cancel();
					userRejection(transactionId, "End User rejection");
				}
			}).show();
	}
	
	/**
	 * 安装流程
	 */
	private void installProfile() {
		try {
			//获取SBPP
			ProfileUtil util = new ProfileUtil();
			String[] SBPP = util.generateSBPP(BPP);
			for (int i = 0; i < SBPP.length; i++) {
				loadBoundProfilePackage(SBPP[i]);
			}
			final String profileInstallationResultData = "80021234BF2F021234";
			final String euiccSignPIR = "5F37021234";
			new AsyncTask<String, Integer, String>() {
	
				@Override
				protected void onPreExecute() {
					description.setText("安装中...");
				};
				
				@Override
				protected String doInBackground(String... params) {
					//调用ES9+.handleNotification
					return TlsES9PlusManager.getInstance().handleNotification(profileInstallationResultData, euiccSignPIR, params[0], Integer.parseInt(params[1]));
				}
				
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					removeNotificationFromList();
					Toast.makeText(DownloadProfileAvtivity2.this, "安装成功", 0).show();
					finish();
				}
			}.execute(GlobalParams.DP_IP_PORT);
		} catch (Exception e){
			PromptManager.showErrorDialog(this, "处理异常：" + e.getMessage());
			finish();
		}
	}
	
	/**
	 * 用户拒绝流程
	 * @param transactionId
	 * @param reason
	 */
	private void userRejection(final String transactionId, final String reason) {
		final String cancelSessionResponse = cancelSessionForEuicc(transactionId, reason);
		final String euiccCancelSessionSigned = "123456789000";
		final String euiccCancelSessionSignature = "5F370412345678";
		new AsyncTask<String, Integer, String>() {

			@Override
			protected void onPreExecute() {
				description.setText("用户拒绝处理中...");
			};
			
			@Override
			protected String doInBackground(String... params) {
				//调用ES9+.cancelSession
				return TlsES9PlusManager.getInstance().cancelSession(transactionId, euiccCancelSessionSigned, euiccCancelSessionSignature, params[0],Integer.parseInt(params[1]));
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				Toast.makeText(DownloadProfileAvtivity2.this, "用户取消，原因："+reason, 1).show();
				finish();
			}
		}.execute(GlobalParams.DP_IP_PORT);
	}
	
	/**
	 * 获取smdp+地址
	 * @param downloadType
	 * @param result
	 * @return
	 */
	private String getDpAddress(String downloadType, String result) {
		Log.i(TAG, "getDpAddress: downloadType = "+ downloadType + " result = " + result);
		if ("3".equals(downloadType)) {//TODO
			return "smdp.gsma.com";
		}
		return null;
	}
	
	/**
	 * 获取eUICCInfo
	 * @return
	 */
	private String getEuiccInfo() {
		SharedPreferences sharedPreferences = getSharedPreferences("EuiccInfo", BaseActivity.MODE_PRIVATE);
		String euiccInfo = sharedPreferences.getString("euiccInfo", "");
		if ("".equals(euiccInfo)) {
//			String apdu = "";//TODO
//			euiccInfo = cardMain.getEUICCInfo(apdu);
//			SharedPreferences.Editor editor = sharedPreferences.edit(); 
//			editor.putString("euiccInfo", euiccInfo);
//			editor.commit();
			euiccInfo = "8202000CA90434567890AA02hhfu";
		}
		Log.i(TAG, "euiccInfo: "+ euiccInfo);
		return euiccInfo;
	}
	
	/**
	 * 获取随机数，调用ES10b.getEUICCChallenge
	 * @return
	 */
	private String getEUICCChallenge() {
//		String apdu = "";//TODO
//		return cardMain.getEUICCChallenge(apdu);
		return "ZVVpY2NDaGFsbGVuZ2VFeGFtcGxlQmFzZTY0oUFZuQnNZVE5D";
	}
	
	/**
	 * 检查dp地址
	 * @return
	 */
	private boolean checkDpAddress() {
		return true;
	}
	
	/**
	 * 生成上下文参数
	 * @return
	 */
	private String generateCtxParams1() {
		return null;
	}
	
	/**
	 * 调用ES10b.authenticateServer
	 * @param smdpSigned1
	 * @param smdpSignature1
	 * @param euiccCiPKIdToBeUsed
	 * @param cert
	 * @param ctxParams1
	 * @return
	 */
	private AuthenticateServerResponse authenticateServer(String smdpSigned1, String smdpSignature1, String euiccCiPKIdToBeUsed, 
			String cert, String ctxParams1) {
//		String apdu = "";//TODO
//		return cardMain.authenticateServer(apdu);
		AuthenticateServerResponse resp = new AuthenticateServerResponse();
		resp.setEuiccSigned1("84021234BF220F810301020382030102038303010203");
		resp.setEuiccSignature1("euiccSignature1Here");
		resp.setEuiccCertificate("euiccCertificateHere");
		resp.setEumCertificate("eumCertificateHere");
		return resp;
	}
	
	/**
	 * 获取RAT
	 * @return
	 */
	private String getRAT() {
		SharedPreferences sharedPreferences = getSharedPreferences("RAT", BaseActivity.MODE_PRIVATE);
		String RAT = sharedPreferences.getString("RAT", "");
		if ("".equals(RAT)) {
			String apdu = "";//TODO
			RAT = cardMain.getRAT(apdu);
			SharedPreferences.Editor editor = sharedPreferences.edit(); 
			editor.putString("RAT", RAT);
			editor.commit();
		}
		return RAT;
	}
	
	/**
	 * 获取已安装Profile列表
	 * @return
	 */
	private String getProfilesInfo() {
		SharedPreferences sharedPreferences = getSharedPreferences("ProfilesInfo", BaseActivity.MODE_PRIVATE);
		String profilesInfo = sharedPreferences.getString("profilesInfo", "");
		if ("".equals(profilesInfo)) {
			String apdu = "";//TODO
			profilesInfo =  cardMain.getProfilesInfo(apdu);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("profilesInfo", profilesInfo);
			editor.commit();
		}
		return profilesInfo;
	}
	
	/**
	 * 调用ES10b.cancelSession
	 * @param transactionID
	 * @param reason
	 * @return
	 */
	private String cancelSessionForEuicc(String transactionID, String reason) {
//		String apdu = "";//TODO
//		return cardMain.cancelSession(apdu);
		return "ddaa56776";
	}
	
	/**
	 * 调用ES10b.prepareDownload
	 * @param smdpSigned2
	 * @param smdpSignature2
	 * @param cert
	 * @param code
	 * @return
	 */
	private PrepareDownloadResponse prepareDownload(String smdpSigned2, String smdpSignature2, String cert , String code) {
//		String apdu = "";//TODO
//		return cardMain.prepareDownload(apdu);
		PrepareDownloadResponse resp = new PrepareDownloadResponse();
		resp.setEuiccSigned2("12345678qwertyui12345678qwertyui12345678qwertyui12345678qwertyui5F37");
		resp.setEuiccSignature2("euiccSignature2Here");
		return resp;
	}
	
	/**
	 * 调用ES10b.loadBoundProfilePackage
	 * @return
	 */
	private String loadBoundProfilePackage(String apdu) {
//		String apdu = "";//TODO
//		return cardMain.loadBoundProfilePackage(apdu);
		return "9000";
	}
	
	/**
	 * 调用ES10b.removeNotificationFromList
	 * @return
	 */
	private String removeNotificationFromList() {
//		String apdu = "";//TODO
//		return cardMain.removeNotificationFromList(apdu);
		return "success";
	}
	
	/**
	 * 是否包含PPR
	 * @return
	 */
	private boolean isContainPPR() {
		return true;
	}
	
	/**
	 * RAT是否允许PPR
	 * @return
	 */
	private boolean isRATAllowedPPR() {
		return true;
	}
	
	/**
	 * 是否需要确认码
	 * @return
	 */
	private boolean isConfirmationCodeRequired() {
		return true;
	}
	
	/**
	 * 检查元数据
	 */
	private void checkMetadata() {
		
	}
	
	/**
	 * 显示元数据
	 */
	private void showMetadata() {
		
	}
}