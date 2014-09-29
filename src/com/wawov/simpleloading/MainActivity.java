package com.wawov.simpleloading;

import com.wawov.ui.SimpleDialog;
import com.wawov.ui.SimpleDialog.ISimpleDialogListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnLoading;

	private SimpleDialog simpleDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnLoading = (Button) findViewById(R.id.btnLoading);
		btnLoading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "show", Toast.LENGTH_SHORT)
						.show();
				simpleDialog = new SimpleDialog(MainActivity.this, true);
				simpleDialog.setiSimpleDialogListener(new DialogListener());
				simpleDialog.show();
				timeHandler.post(timeRunnable);
				//new Thread(timeRunnable).start();
			}
		});
	}


	Runnable timeRunnable = new Runnable() {
		int i = 0;
		@Override
		public void run() {
			i +=1;
			Log.d("timeRunnable", "I=" + i);
			timeHandler.postDelayed(timeRunnable, 500);
			if(i <= 100){
				timeHandler.sendMessage(timeHandler.obtainMessage(1, i));
			}else{
				timeHandler.sendEmptyMessage(0);
			}
		}
	};

	Handler timeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Log.d("timeHandler", "移除timeRunnable");
				timeHandler.removeCallbacks(timeRunnable);
				break;
			case 1:
				simpleDialog.updateProgress(Integer.parseInt(msg.obj.toString()));
				break;
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class DialogListener implements ISimpleDialogListener {

		@Override
		public void onDismiss() {
			// do someting you want
			Toast.makeText(MainActivity.this, "我要关闭了", 1).show();
			timeHandler.sendEmptyMessage(0);
		}

	}
}
