package com.whty.rsp_lpa_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.whty.rsp_lpa_app.utils.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener{
	
	private Button download;
	private Button download2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		download = (Button) findViewById(R.id.download);
		download2 = (Button) findViewById(R.id.download2);
		download.setOnClickListener(this);
		download2.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.download:
			Intent intent = new Intent(this, DownloadProfileAvtivity.class);
			intent.putExtra("download_type", "3");
			intent.putExtra("result", "");
			startActivity(intent);
			break;
		case R.id.download2:
			Intent intent2 = new Intent(this, DownloadProfileAvtivity2.class);
			intent2.putExtra("download_type", "3");
			intent2.putExtra("result", "");
			startActivity(intent2);
			break;
		}
		
	}
}
