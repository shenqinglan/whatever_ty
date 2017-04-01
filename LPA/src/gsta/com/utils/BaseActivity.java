package gsta.com.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity {
	public Toast mToast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
	}

	public void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ExitApplication.getInstance().removeActivity(this);
	}
}
