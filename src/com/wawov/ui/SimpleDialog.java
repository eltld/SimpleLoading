package com.wawov.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wawov.simpleloading.R;

/**
 * 一个简单的弹出层progress
 * @author viky
 *	@date 2014-09-28
 */
public class SimpleDialog extends PopupWindow {
	
	private Context mContext;

	private PopupWindow mWindow;
	
	private TextView progress;
	
	private boolean isShow = false ;
	
	private ISimpleDialogListener iSimpleDialogListener;
	
	public SimpleDialog(Context context) {
		this.mContext = context;
	}
	
	/**
	 * 如果想显示进度用此构造函数
	 * @param context
	 * @param isShow	是否显示进度，默认不显示，
	 */
	public SimpleDialog(Context context, boolean isShow){
		this.mContext = context;
		this.isShow = isShow;
	}
	
	public void setiSimpleDialogListener(ISimpleDialogListener iSimpleDialogListener) {
		this.iSimpleDialogListener = iSimpleDialogListener;
	}

	/**
	 * 弹出dialog
	 */
	public void show(){
		if(mWindow == null){
			View layout = LayoutInflater.from(mContext).inflate(R.layout.simpleloading, null);
			progress = (TextView) layout.findViewById(R.id.simple_text_progress);
			if(isShow){
				progress.setVisibility(View.VISIBLE);
			}else{
				progress.setVisibility(View.GONE);
			}
			mWindow = new PopupWindow(layout,android.app.ActionBar.LayoutParams.MATCH_PARENT,android.app.ActionBar.LayoutParams.MATCH_PARENT);
			mWindow.setTouchable(true); // 设置popupwindow可点击  
			mWindow.setOutsideTouchable(true);  // 设置popupwindow外部可点击  
			mWindow.setFocusable(true); //获取焦点  
			mWindow.showAtLocation(layout, Gravity.CENTER , 0, 0);
			progress.setFocusableInTouchMode(true);
			progress.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						if(iSimpleDialogListener != null){
							iSimpleDialogListener.onDismiss();
						}
						close();
						return true;
					}
					return false;
				}
			});
			mWindow.update();
		}
	}
	
	/**
	 * 关闭dialog
	 */
	public void close(){
		if(mWindow != null){
			mWindow.dismiss();
			progress = null;
			mWindow = null;
		}
	}
	
	/**
	 * 进度是否可见
	 * @param isShow
	 */
	public void setProgressVisible(boolean isShow){
		this.isShow = isShow;
	}
	
	/**
	 * 更新进度
	 * @param precent	百分百，10%输入10
	 */
	public void updateProgress(int precent){
		if(isShow){
			if(mWindow != null && progress != null){
				progress.setText(precent+"%");
				mWindow.update();
			}
		}
	}
	
	/**
	 * 可实现的接口，用户按back键时，处理所需的事件
	 * @author viky
	 *
	 */
	public interface ISimpleDialogListener{
		public void onDismiss();
	}
}
