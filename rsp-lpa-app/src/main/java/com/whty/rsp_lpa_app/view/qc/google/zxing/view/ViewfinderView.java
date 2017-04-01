/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whty.rsp_lpa_app.view.qc.google.zxing.view;

import java.util.Collection;
import java.util.HashSet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.whty.rsp_lpa_app.R;
import com.whty.rsp_lpa_app.view.qc.google.zxing.camera.CameraManager;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 */
public final class ViewfinderView extends View {
	/**
	 * 刷新界面的时间
	 */
	private static final long ANIMATION_DELAY = 10L;
	private static final int OPAQUE = 0xFF;

	/**
	 * 四个绿色边角对应的长度
	 */
	private int ScreenRate;
	
	/**
	 * 四个绿色边角对应的宽度
	 */
	private static final int CORNER_WIDTH = 5;
	
	/**
	 * 手机的屏幕密度
	 */
	private static float density;
	/**
	 * 字体大小
	 */
	private static final int TEXT_SIZE = 16;
	/**
	 * 字体距离扫描框下面的距离
	 */
	private static final int TEXT_PADDING_TOP = 30;
	
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	
	/**
	 * 将扫描的二维码拍下来，这里没有这个功能，暂时不考虑
	 */
	private Bitmap resultBitmap;
	private Collection<ResultPoint> possibleResultPoints;
	boolean isFirst;
	
	int borderColors[]={getResources().getColor(R.color.holo_green_light),getResources().getColor(R.color.holo_blue_light),getResources().getColor(R.color.holo_orange_light),getResources().getColor(R.color.holo_red_light)};
	
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		density = context.getResources().getDisplayMetrics().density;
		//将像素转换成dp
		ScreenRate = (int)(60 * density);

		paint = new Paint();
		Resources resources = getResources();
		
		resources.getColor(R.color.hui);

		possibleResultPoints = new HashSet<ResultPoint>(5);
	}

	@Override
	public void onDraw(Canvas canvas) {
		//中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}
		
		//获取屏幕的宽和高
		int width = canvas.getWidth();

		//paint.setColor(resultBitmap != null ? resultColor : maskColor);
		
		//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
		//扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
//		paint.setColor(maskColor);
//		canvas.drawRect(0, 0, width, frame.top, paint);
//		canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
//		canvas.drawRect(frame.right, frame.top, width, frame.bottom,paint);
//		canvas.drawRect(0, frame.bottom , width, height, paint);

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			//画扫描框边上的角，总共8个部分
			paint.setColor(borderColors[0]);
			canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,frame.top + CORNER_WIDTH, paint);
			canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top+ ScreenRate, paint);
			
			paint.setColor(borderColors[1]);
			canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,frame.top + CORNER_WIDTH, paint);
			canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top+ ScreenRate, paint);
			
			paint.setColor(borderColors[2]);
			canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left+ ScreenRate, frame.bottom, paint);
			canvas.drawRect(frame.left, frame.bottom - ScreenRate,frame.left + CORNER_WIDTH, frame.bottom, paint);
			
			paint.setColor(borderColors[3]);
			canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH,frame.right, frame.bottom, paint);
			canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate,frame.right, frame.bottom, paint);
			
			//画扫描框下面的字
			paint.setColor(Color.WHITE);
			paint.setTextSize(TEXT_SIZE * density);
			paint.setAlpha(0x40);
			paint.setTypeface(Typeface.create("System", Typeface.BOLD));
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(getResources().getString(R.string.scan_tip), width/2, frame.bottom + TEXT_PADDING_TOP *density, paint);
			
			Collection<ResultPoint> currentPossible = possibleResultPoints;
			if (currentPossible.isEmpty()) {
			}
			
			//只刷新扫描框的内容，其他地方不刷新
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,frame.right, frame.bottom);
			
		}
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

}
