package com.whty.rsp_lpa_app;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.whty.rsp_lpa_app.utils.BaseActivity;
public class SplashActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout splash;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
		
        splash = (RelativeLayout) findViewById(R.id.rl_root_splash);
        splash.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		enterHome();
		AlphaAnimation aa = new AlphaAnimation(1.0f,0.2f);
		aa.setDuration(50);
		aa.setFillAfter(true);
		splash.startAnimation(aa);
	}
	
	private void enterHome() {
//		Intent intent = new Intent(this,CardInfoActivity.class);
//		startActivity(intent);
//		finish();
	}

}





