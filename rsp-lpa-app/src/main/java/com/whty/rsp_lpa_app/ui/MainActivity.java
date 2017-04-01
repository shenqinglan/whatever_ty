package com.whty.rsp_lpa_app.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.whty.euicc.rsp.lpa.profile.ProfileUtil;
import com.whty.euicc.rsp.oma.CardMain;
import com.whty.euicc.rsp.oma.callback.OMAServiceCallBack;
import com.whty.euicc.rsp.oma.common.LogUtil;
import com.whty.euicc.rsp.packets.message.request.AuthenticateClientReq;
import com.whty.euicc.rsp.packets.message.request.CancelSessionReq;
import com.whty.euicc.rsp.packets.message.request.GetBoundProfilePackageReq;
import com.whty.euicc.rsp.packets.message.request.HandleNotificationReq;
import com.whty.euicc.rsp.packets.message.response.AuthenticateClientResp;
import com.whty.euicc.rsp.packets.message.response.GetBoundProfilePackageResp;
import com.whty.euicc.rsp.packets.message.response.InitiateAuthenticationResp;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.tls.TlsManager;
import com.whty.euicc.rsp.utils.LpaUtils;
import com.whty.euicc.rsp.utils.SHA256Util;
import com.whty.euicc.rsp.utils.ToTLV;
import com.whty.euicc.rsp.utils.Tools;
import com.whty.rsp_lpa_app.R;
import com.whty.rsp_lpa_app.bean.EnableMessage;
import com.whty.rsp_lpa_app.utils.BaseActivity;
import com.whty.rsp_lpa_app.utils.NetUtil;
import com.whty.rsp_lpa_app.utils.PromptManager;
import com.whty.rsp_lpa_app.utils.PropertiesUtils;

public class MainActivity extends Activity implements OnClickListener,
		OMAServiceCallBack, OnItemClickListener, OnItemLongClickListener {
	private static final String TAG = "MainActivity";
	/** 卡片AID */
	private static final String AID=PropertiesUtils.getValue("AID");
	private static final String SM_DP_IP = PropertiesUtils.getValue("SM_DP_IP");
	private static final int SM_DP_PORT = Integer.parseInt(PropertiesUtils.getValue("SM_DP_PORT"));
	private CardMain cardMain;
	private TlsManager tlsManager;
	private Button download_install;
	private Button list_profile;
	private Button enable;
	private Button disable;
	private Button delete;
	private Dialog downloaddialog;
	private Dialog installdialog;
	private String transactionId;
	private String profileMetadata;
	private String smdpSigned2;
	private String smdpSignature2;
	private String smdpCertificate;
	private String confirmCode;
	private String BPP;
	private String profileInstallationResultData;
	private String euiccSignPIR;
	private List<Map<String,String>> profileData = new ArrayList<Map<String,String>>();
	private ListView profileListView;
	private SimpleAdapter profileListAdapter;
	private EditText editNickName;
	private Dialog profileDialog;
	private Dialog deleteDialog;
	private int position;
	private final static int ENABLE = 1;
	private final static int DISABLE = 2;
	private final static int DELETE = 3;
	private List<Map<String,String>> profileInfo = null;
    
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ENABLE:
				EnableMessage enableMessage = (EnableMessage) msg.getData().getSerializable("EnableMessage");
				if (!StringUtils.equals("1", cardMain.removeEnableNotification(enableMessage.getIccid(), enableMessage.getResult()))) {
					Toast.makeText(MainActivity.this, "删除卡片通知出错", 0).show();
					return;
				}
				profileData.clear();
				profileInfo = getProfilesInfo(null);
				if (profileInfo == null) {
					Toast.makeText(MainActivity.this, "获取Profile信息出错", 0).show();
				} else {
					profileData.addAll(profileInfo);
					//updateButton(profileData.get(position).get("state"));
					dismissProfileDialog();
					updateListView();
				}
				
				break;
				
			case DISABLE:
				if (!StringUtils.equals("1", cardMain.removeDisableNotification((String)msg.obj))) {
					Toast.makeText(MainActivity.this, "删除卡片通知出错", 0).show();
					return;
				}
				profileData.clear();
				profileInfo = getProfilesInfo(null);
				if (profileInfo == null) {
					Toast.makeText(MainActivity.this, "获取Profile信息出错", 0).show();
				} else {
					profileData.addAll(profileInfo);
					//updateButton(profileData.get(position).get("state"));
					dismissProfileDialog();
					updateListView();
				}
				
				break;
				
			case DELETE:
				if (!StringUtils.equals("1", cardMain.removeDeleteNotification((String)msg.obj))) {
					Toast.makeText(MainActivity.this, "删除卡片通知出错", 0).show();
					return;
				}
				profileData.clear();
				profileInfo = getProfilesInfo(null);
				if (profileInfo == null) {
					Toast.makeText(MainActivity.this, "获取Profile信息出错", 0).show();
				} else {
					profileData.addAll(profileInfo);
					//updateButton(profileData.get(position).get("state"));
					dismissProfileDialog();
					updateListView();
				}
				
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		cardMain = new CardMain(MainActivity.this,AID);
		tlsManager = new TlsManager(SM_DP_IP, SM_DP_PORT);
		initViews();
		cardMain.initCard(MainActivity.this);
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		download_install = (Button) findViewById(R.id.download_install);
		list_profile = (Button) findViewById(R.id.list_profile);
		
		download_install.setOnClickListener(this);
		list_profile.setOnClickListener(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			cardMain.closeSEService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.download_install:
			downloadAndInstall();
			break;
			
		case R.id.list_profile:
			showProfileList();
			break;	
			
		case R.id.btn_enable:
			String enable_iccid = profileData.get(position).get("iccid");
			String result = cardMain.checkAnyEnableProfile();
			if (cardMain.enableProfile(enable_iccid, "00")) {
				cardMain.closeSEService();
				cardMain.initCard(MainActivity.this);
				EnableMessage data = new EnableMessage(enable_iccid, result);
				Bundle b = new Bundle();
				b.putSerializable("EnableMessage", data);
				Message msg = Message.obtain();
				msg.what = ENABLE;
				msg.setData(b);
				handler.sendMessage(msg);
				Toast.makeText(getApplicationContext(), "启用成功", 0).show();
			} else {
				Toast.makeText(getApplicationContext(), "启用失败", 0).show();
			}
			break;
			
		case R.id.btn_disable:
			String disable_iccid = profileData.get(position).get("iccid");
			if (cardMain.disableProfile(disable_iccid, "00")) {
				cardMain.closeSEService();
				cardMain.initCard(MainActivity.this);
				Message msg = Message.obtain();
				msg.what = DISABLE;
				msg.obj = disable_iccid;
				handler.sendMessage(msg);
				Toast.makeText(getApplicationContext(), "禁用成功", 0).show();
			} else {
				Toast.makeText(getApplicationContext(), "禁用失败", 0).show();
			}
			break;
			
		case R.id.btn_delete:
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("是否删除此Profile").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if (deleteDialog != null && deleteDialog.isShowing()) {
						deleteDialog.dismiss();
					}
					String delete_iccid = profileData.get(position).get("iccid");
					if (cardMain.deleteProfile(delete_iccid)) {
						cardMain.closeSEService();
						cardMain.initCard(MainActivity.this);
						Message msg = Message.obtain();
						msg.what = DELETE;
						msg.obj = delete_iccid;
						handler.sendMessage(msg);
						Toast.makeText(getApplicationContext(), "删除成功", 0).show();
					} else {
						Toast.makeText(getApplicationContext(), "删除失败", 0).show();
					}
				}
			}).setNegativeButton("取消", null);
			deleteDialog = builder.create();
			deleteDialog.show();
			break;

		}

	}


	@Override
	public void omaServiceCallBack() {
		LogUtil.e(TAG, "initCard Success!");
	}
	
	/**
	 * 下载安装流程
	 */
	private void downloadAndInstall() {
		// 获取smdp地址，euiccinfo1，随机数
		cardMain.setDefaultDpAddressApdu("00010203");
		final String smdpAdress = getDpAddress();
		final String euiccinfo1 = getEuiccInfo1();
		final String eUICCChallenge = getEUICCChallenge();
		
		if (!isNetConnected()) {
			return;
		}
		new AsyncTask<String, Integer, InitiateAuthenticationResp>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(MainActivity.this, "初始化双向认证参数...");
			}
			
			@Override
			protected InitiateAuthenticationResp doInBackground(String... params) {
				//调用ES9+.initiateAuthentication
				return tlsManager.initiateAuthentication(eUICCChallenge, euiccinfo1, smdpAdress);
			}
			
			@Override
			protected void onPostExecute(InitiateAuthenticationResp result) {
				super.onPostExecute(result);
				PromptManager.closeProgressDialog();
				if (result == null) {
					Toast.makeText(MainActivity.this, "获取数据失败,请检查网络状态", 0).show();
					return;
				}
				transactionId = result.getTransactionId();
				Log.i(TAG, "transactionId: "+result.getTransactionId());
				Log.i(TAG, "serverSigned1: "+result.getServerSigned1());
				Log.i(TAG, "serverSignature1: "+result.getServerSignature1());
				Log.i(TAG, "euiccCiPKIdTobeUsed: "+result.getEuiccCiPKIdTobeUsed());
				Log.i(TAG, "serverCertificate: "+result.getServerCertificate());
				authenticateClient(result);
			}
		}.execute();
		
	}
	
	/**
	 * 调用ES9+.authenticateClient
	 * @param initiateAuthenticationResp
	 */
	private void authenticateClient(final InitiateAuthenticationResp initiateAuthenticationResp) {
		StatusCodeData codeData = initiateAuthenticationResp.getHeader().getFunctionExecutionStatus().getStatusCodeData();
		if (!"00".equals(codeData.getReasonCode())) {
			PromptManager.showErrorDialog(this, codeData.getMessage());
			return;
		}
		//验证dp地址
		if (!LpaUtils.checkDpAddress()) {
			PromptManager.showErrorDialog(this, "SM-DP+地址不匹配");
			return;
		}
		//生成上下文参数
		String ctxParams1 = LpaUtils.generateCtxParams1();
		//调用ES10b.authenticateServer
		final AuthenticateClientReq authenticateClientReq = authenticateServer(initiateAuthenticationResp.getServerSigned1(), 
															   initiateAuthenticationResp.getServerSignature1(), 
															   initiateAuthenticationResp.getEuiccCiPKIdTobeUsed(), 
															   initiateAuthenticationResp.getServerCertificate(), 
															   ctxParams1);
		
		if (!isNetConnected()) {
			return;
		}
		new AsyncTask<String, Integer, AuthenticateClientResp>() {

			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(MainActivity.this, "双向认证中...");
			}
			
			@Override
			protected AuthenticateClientResp doInBackground(String... params) {
				return tlsManager.authenticateClient(transactionId, authenticateClientReq.getEuiccSigned1(), 
						authenticateClientReq.getEuiccSignature1(), 
						authenticateClientReq.getEuiccCertificate(),
						authenticateClientReq.getEumCertificate());
			}
			
			@Override
			protected void onPostExecute(AuthenticateClientResp result) {
				super.onPostExecute(result);
				PromptManager.closeProgressDialog();
				if (result == null) {
					Toast.makeText(MainActivity.this, "获取数据失败,请检查网络状态", 0).show();
					return;
				}
				checkProfile(result);
				Toast.makeText(MainActivity.this, "双向认证成功", 0).show();
				
			}
		}.execute();
		
	}
	
	/**
	 * 检查profile
	 * @param authenticateClientResp
	 */
	private void checkProfile(AuthenticateClientResp authenticateClientResp) {
		StatusCodeData codeData = authenticateClientResp.getHeader().getFunctionExecutionStatus().getStatusCodeData();
		if (!"00".equals(codeData.getReasonCode())) {
			PromptManager.showErrorDialog(this, codeData.getMessage());
			return;
		}
		profileMetadata = authenticateClientResp.getProfileMetadata();
		smdpSigned2 = authenticateClientResp.getSmdpSigned2();
		smdpSignature2 = authenticateClientResp.getSmdpSignature2();
		smdpCertificate = authenticateClientResp.getSmdpCertificate();
		Log.i(TAG, "profileMetadata: "+profileMetadata);
		Log.i(TAG, "smdpSigned2: "+smdpSigned2);
		Log.i(TAG, "smdpSignature2: "+smdpSignature2);
		Log.i(TAG, "smdpCertificate: "+smdpCertificate);

		//检查是否包含PPR
		if(LpaUtils.isContainPPR(profileMetadata)) {
			//getRAT();
			if (LpaUtils.isRATAllowedPPR()) {
				AlertDialog.Builder builder = new Builder(this);
				builder.setIcon(R.drawable.ic_launcher)
					   .setTitle(R.string.app_name)
					   .setMessage("是否同意此PPR").setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							confirmationCodeDialog();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								userRejection(transactionId, "00");
							}
						}).show();
			} else {
				userRejection(transactionId, "00");
				return;
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
		if (LpaUtils.isConfirmationCodeRequired(smdpSigned2)) {
			View view = View.inflate(this, R.layout.confirmation_dailog, null);
			code = (EditText) view.findViewById(R.id.confirm_code);
			AlertDialog.Builder builder = new Builder(this);
			final AlertDialog dialog = builder.setIcon(R.drawable.ic_launcher)
		   	   .setTitle(R.string.app_name)
		   	   .setView(view)
		   	   .setPositiveButton("确定", null)
		   	   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					userRejection(transactionId, "00");
				}
			})
		   	   .setCancelable(false).show();
			
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					confirmCode = SHA256Util.Encrypt(SHA256Util.Encrypt(code.getText().toString(), "SHA-256") + transactionId.substring(4), "SHA-256");
					LogUtil.e(TAG, "confirmCode: "+confirmCode);
					if (null == confirmCode || "".equals(confirmCode)) {
						Toast.makeText(MainActivity.this, "确认码不能为空", 0).show();
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
							userRejection(transactionId, "00");
						}
					}
				});
				
			}
		};
		downloadTimer.schedule(downloadTask, 30000);
		AlertDialog.Builder builder = new Builder(this);
		downloaddialog = builder.setIcon(R.drawable.ic_launcher)
			   	   .setTitle(R.string.app_name)
			   	   .setMessage("是否确定下载此Profile")
		   	   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					downloadTimer.cancel();
					prepareDownload();
				}
			}).setCancelable(false).setNeutralButton("稍后", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					downloadTimer.cancel();
					userRejection(transactionId, "00");
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					downloadTimer.cancel();
					userRejection(transactionId, "00");
				}
			}).show();
		
	}
	
	/**
	 * 下载准备
	 */
	private void prepareDownload() {
		//准备下载
		final GetBoundProfilePackageReq prepareDownloadResponse = prepareDownload(smdpSigned2, smdpSignature2, smdpCertificate, confirmCode);
		if (!isNetConnected()) {
			return;
		}
		new AsyncTask<String, Integer, GetBoundProfilePackageResp>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(MainActivity.this, "下载中...");
			};
			
			@Override
			protected GetBoundProfilePackageResp doInBackground(String... params) {
				//调用ES9+.getBoundProfilePackage
				return tlsManager.getBoundProfilePackage(transactionId, 
						prepareDownloadResponse.getEuiccSigned2(), 
						prepareDownloadResponse.getEuiccSignature2());
			}
			
			@Override
			protected void onPostExecute(GetBoundProfilePackageResp result) {
				super.onPostExecute(result);
				PromptManager.closeProgressDialog();
				if (result == null) {
					Toast.makeText(MainActivity.this, "获取数据失败,请检查网络状态", 0).show();
					return;
				}
				confirmInstall(result);
				Toast.makeText(MainActivity.this, "下载成功", 0).show();
			}
		}.execute();
	}
	
	/**
	 * 确认安装
	 * @param getBoundProfilePackageResp
	 */
	private void confirmInstall(GetBoundProfilePackageResp getBoundProfilePackageResp) {
		StatusCodeData codeData = getBoundProfilePackageResp.getHeader().getFunctionExecutionStatus().getStatusCodeData();
		if (!"00".equals(codeData.getReasonCode())) {
			PromptManager.showErrorDialog(this, codeData.getMessage());
			return;
		}
		
		BPP = getBoundProfilePackageResp.getBoundProfilePackage();
		Log.i(TAG, "BPP: "+BPP);
		//认证metadata
		LpaUtils.checkMetadata();
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
							userRejection(transactionId, "00");
						}
					}
				});
			}
		};
		installTimer.schedule(installTask, 30000);
		//用户确认是否安装Profile
		AlertDialog.Builder builder = new Builder(this);
		installdialog = builder.setIcon(R.drawable.ic_launcher)
			   	   .setTitle(R.string.app_name)
			   	   .setMessage("是否确定安装此Profile")
		   	   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					installTimer.cancel();
					installProfile();
				}
			}).setCancelable(false).setNeutralButton("稍后", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					installTimer.cancel();
					userRejection(transactionId, "00");
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					installTimer.cancel();
					userRejection(transactionId, "00");
				}
			}).show();
	}
	
	/**
	 * 安装流程
	 */
	private void installProfile() {
		try {
			if (!isNetConnected()) {
				return;
			}
			
			new AsyncTask<String, Integer, String>() {
				
				@Override
				protected void onPreExecute() {
					//获取SBPP
					PromptManager.showProgressDialog(MainActivity.this, "安装中...");
					ProfileUtil util = new ProfileUtil();
					List<String[]> SBPP = util.generateSBPP(BPP);
					HandleNotificationReq handleNotificationReq = loadBoundProfilePackage(SBPP);
					profileInstallationResultData = handleNotificationReq.getProfileInstallationResultData();
					euiccSignPIR = handleNotificationReq.getEuiccSignPIR();
				}
				
				@Override
				protected String doInBackground(String... params) {
					//调用ES9+.handleNotification
					return tlsManager.handleNotification(profileInstallationResultData, euiccSignPIR);
				}
				
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					PromptManager.closeProgressDialog();
					if (result == null) {
						Toast.makeText(MainActivity.this, "获取数据失败,请检查网络状态", 0).show();
						return;
					}
					if (StringUtils.equals("1", cardMain.removeInstallNotification(ToTLV.determineTLV(profileMetadata, "5A", "91")))) {
						Toast.makeText(MainActivity.this, "安装成功", 0).show();
					} else {
						Toast.makeText(MainActivity.this, "删除卡片通知失败", 0).show();
					}
				}
			}.execute();
		} catch (Exception e){
			PromptManager.showErrorDialog(this, e.getMessage());
		}
	}
	
	/**
	 * 用户拒绝流程
	 * @param transactionId
	 * @param reason
	 */
	private void userRejection(final String transactionId, final String reason) {
		CancelSessionReq cancelSessionResponse = cancelSessionForEuicc(transactionId, reason);
		final String euiccCancelSessionSigned = cancelSessionResponse.getEuiccCancelSessionSigned();
		final String euiccCancelSessionSignature = cancelSessionResponse.getEuiccCancelSessionSignature();

		if (!isNetConnected()) {
			return;
		}
		new AsyncTask<String, Integer, String>() {
			
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(MainActivity.this, "用户拒绝处理中...");
			};
			
			@Override
			protected String doInBackground(String... params) {
				//调用ES9+.cancelSession
				return tlsManager.cancelSession(transactionId, euiccCancelSessionSigned, euiccCancelSessionSignature);
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				PromptManager.closeProgressDialog();
				if (result == null) {
					Toast.makeText(MainActivity.this, "获取数据失败,请检查网络状态", 0).show();
					return;
				}
				Toast.makeText(MainActivity.this, "用户取消，原因："+reason, 1).show();
			}
		}.execute();
	}
	
	/**
	 * 获取smdp+地址
	 * @return
	 */
	private String getDpAddress() {
		return cardMain.getEuiccConfiguredAddresses();
	}
	
	/**
	 * 获取eUICCInfo1
	 * @return
	 */
	private String getEuiccInfo1() {
//		SharedPreferences sharedPreferences = getSharedPreferences("EuiccInfo", BaseActivity.MODE_PRIVATE);
		String euiccInfo = cardMain.getEUICCInfo(1);
//		String euiccInfo = sharedPreferences.getString("euiccInfo", "");
//		if ("".equals(euiccInfo)) {
//			euiccInfo = cardMain.getEUICCInfo(1);
//			SharedPreferences.Editor editor = sharedPreferences.edit(); 
//			editor.putString("euiccInfo", euiccInfo);
//			editor.commit();
//		}
		Log.i(TAG, "euiccInfo: "+ euiccInfo);
		return euiccInfo;
	}
	
	/**
	 * 获取随机数，调用ES10b.getEUICCChallenge
	 * @return
	 */
	private String getEUICCChallenge() {
		return cardMain.getEUICCChallenge().toUpperCase();
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
	private AuthenticateClientReq authenticateServer(String smdpSigned1, String smdpSignature1, String euiccCiPKIdToBeUsed, 
			String cert, String ctxParams1) {
		return cardMain.authenticateServer(smdpSigned1, smdpSignature1, euiccCiPKIdToBeUsed, cert, ctxParams1);
	}
	
	
	/**
	 * 获取RAT
	 * @return
	 */
	private String getRAT() {
		SharedPreferences sharedPreferences = getSharedPreferences("RAT", BaseActivity.MODE_PRIVATE);
		String RAT = sharedPreferences.getString("RAT", "");
		if ("".equals(RAT)) {
			RAT = cardMain.getRAT();
			SharedPreferences.Editor editor = sharedPreferences.edit(); 
			editor.putString("RAT", RAT);
			editor.commit();
		}
		return RAT;
	}
	
	
	/**
	 * 调用ES10b.cancelSession
	 * @param transactionID
	 * @param reason
	 * @return
	 */
	private CancelSessionReq cancelSessionForEuicc(String transactionID, String reason) {
		return cardMain.cancelSession(transactionID, reason);
	}
	
	/**
	 * 调用ES10b.prepareDownload
	 * @param smdpSigned2
	 * @param smdpSignature2
	 * @param cert
	 * @param code
	 * @return
	 */
	private GetBoundProfilePackageReq prepareDownload(String smdpSigned2, String smdpSignature2, String cert , String code) {
		return cardMain.prepareDownload(smdpSigned2, smdpSignature2, cert, code);
	}
	
	/**
	 * 调用ES10b.loadBoundProfilePackage
	 * @return
	 */
	private HandleNotificationReq loadBoundProfilePackage(List<String[]> data) {
		return cardMain.loadBoundProfilePackage(data);
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
	 * 显示元数据
	 */
	private void showMetadata() {
		
	}
	
	
	private void showProfileList() {
		View view = View.inflate(MainActivity.this, R.layout.profile_list_view, null);
		profileListView = (ListView) view.findViewById(R.id.profile_listview);
		profileData.clear();
		profileInfo = getProfilesInfo(null);
		if (profileInfo == null) {
			Toast.makeText(MainActivity.this, "获取Profile信息出错", 0).show();
		} else {
			profileData.addAll(profileInfo);
			profileListAdapter = new SimpleAdapter(MainActivity.this, profileData, R.layout.profile_listview_item, new String[]{"iccid","nickName","state"}, new int[]{R.id.profile_iccid,R.id.profile_nickname,R.id.profile_status});
			profileListView.setAdapter(profileListAdapter);
			profileListView.setOnItemClickListener(this);
			profileListView.setOnItemLongClickListener(this);
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setTitle("Profile 列表").setView(view).show();
		}
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		View view = View.inflate(MainActivity.this, R.layout.edit_nickname_view, null);
		final Map<String,String> map = profileData.get(arg2);
		editNickName = (EditText) view.findViewById(R.id.edit_nickname);
		editNickName.setText(map.get("nickName"));
		AlertDialog.Builder builder = new Builder(arg0.getContext());
		builder.setTitle("设置昵称").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if (StringUtils.isEmpty(editNickName.getText().toString())) {
					Toast.makeText(MainActivity.this, "昵称不能为空", 0).show();
				} else {
					String profileNickname = Tools.encodeHexString(editNickName.getText().toString());
					cardMain.setNickname(map.get("iccid"), profileNickname);
					updateListView();
				}
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		position = arg2;
		View view = View.inflate(this, R.layout.profile_manager_view, null);
		enable = (Button) view.findViewById(R.id.btn_enable);
		disable = (Button) view.findViewById(R.id.btn_disable);
		delete = (Button) view.findViewById(R.id.btn_delete);
		
		enable.setOnClickListener(this);
		disable.setOnClickListener(this);
		delete.setOnClickListener(this);
		updateButton(profileData.get(arg2).get("state"));
		AlertDialog.Builder builder = new Builder(arg0.getContext());
		builder.setTitle("Profile管理").setView(view);
		profileDialog = builder.create();
		profileDialog.show();
	}
	
	private void updateButton(String state) {
		if(StringUtils.equals("启用", state)) {
			enable.setBackgroundColor(Color.GRAY);
			enable.setClickable(false);
			enable.setFocusable(false);
			
			disable.setBackgroundColor(Color.WHITE);
			disable.setClickable(true);
			disable.setFocusable(true);
			
			delete.setBackgroundColor(Color.GRAY);
			delete.setClickable(false);
			delete.setFocusable(false);
		} else if (StringUtils.equals("禁用", state)) {
			enable.setBackgroundColor(Color.WHITE);
			enable.setClickable(true);
			enable.setFocusable(true);
			
			disable.setBackgroundColor(Color.GRAY);
			disable.setClickable(false);
			disable.setFocusable(false);
			
			delete.setBackgroundColor(Color.WHITE);
			delete.setClickable(true);
			delete.setFocusable(true);
		}
	}
	
	private void updateListView(){
		profileData.clear();
		profileInfo = getProfilesInfo(null);
		if (profileInfo == null) {
			Toast.makeText(MainActivity.this, "获取Profile信息出错", 0).show();
		} else {
			profileData.addAll(profileInfo);
			profileListAdapter.notifyDataSetChanged();
		}
	}
	
	private void dismissProfileDialog() {
		if (profileDialog != null && profileDialog.isShowing()) {
			profileDialog.dismiss();
		}
	}
	
	private boolean isNetConnected() {
		if (!NetUtil.checkNet(MainActivity.this)) {
			PromptManager.showErrorDialog(MainActivity.this, "无网络连接");
			return false;
		}
		return true;
	}
	
	private List<Map<String,String>> getProfilesInfo(String isdp) {
		try {
			return cardMain.getProfilesInfo(isdp);
		} catch (RuntimeException e){
			return null;
		}
	}
}
