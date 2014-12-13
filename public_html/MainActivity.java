package com.example.inf133;


import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView mTextViewOrientLabel;
	private TextView yAxisLabel;
	private TextView zAxisLabel;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private SensorEventListener mEventListenerOrient;
	private float lastOrientValue;
	private float yaxis;
	private float zaxis;
	private MediaPlayer mp;
	private AssetFileDescriptor afd;
	private AssetFileDescriptor tsk;
	private AssetFileDescriptor ke;
	private AssetFileDescriptor psh;
	private AssetFileDescriptor tom;
	
	private AssetFileDescriptor latch;
	private AssetFileDescriptor fastcar;
	private AssetFileDescriptor raise;
	private AssetFileDescriptor realworld;
	private AssetFileDescriptor thinkin;
	
	private void updateUI(){
		runOnUiThread(new Runnable(){
			
			public void run(){
				mTextViewOrientLabel.setText(" "+lastOrientValue);
				yAxisLabel.setText(" "+yaxis);
				zAxisLabel.setText(" "+zaxis);
			}
		});
	}
	
	synchronized void playAudio(AssetFileDescriptor afd){
		if(mp.isPlaying()){
			return;
		}
		else{
			
			try {
				mp.reset();
				mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
				mp.prepare();
				mp.start();
			} catch (IllegalArgumentException e) {
				Log.d("playAudio:",""+e+"\n afd:"+afd.toString());
			} catch (IllegalStateException e) {
				Log.d("playAudio:",""+e+"\n afd:"+afd.toString());				
			} catch (IOException e) {
				Log.d("playAudio:",""+e+"\n afd:"+afd.toString());				
			}
			
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTextViewOrientLabel = (TextView) findViewById(R.id.editText1);
		yAxisLabel = (TextView) findViewById(R.id.editText2);
		zAxisLabel = (TextView) findViewById(R.id.editText3);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		mEventListenerOrient = new SensorEventListener() {

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				float[] values = event.values;
				lastOrientValue = values[0];
				yaxis = values[1];
				zaxis = values[2];
				updateUI();
				// X-Axis: rotate the phone left and right
				// Y-Axis: rotate the phone forward and backward
				if (lastOrientValue > 15) {
					playAudio(latch);
				}
				else if (yaxis > 20) {
					playAudio(fastcar);
				}
				else if (lastOrientValue < -15) {
					playAudio(raise);
				}
				else if (yaxis < -15) {
					playAudio(realworld);
				}
				else if (yaxis < 14 && yaxis > -14) {
					playAudio(thinkin);
				}
			}
			
		};
		
		mp = new MediaPlayer();
		afd = getApplicationContext().getResources().openRawResourceFd(R.raw.bass);
		tsk = getApplicationContext().getResources().openRawResourceFd(R.raw.tsk);
		ke = getApplicationContext().getResources().openRawResourceFd(R.raw.ke);
		psh = getApplicationContext().getResources().openRawResourceFd(R.raw.psh);
		tom = getApplicationContext().getResources().openRawResourceFd(R.raw.tom);
		
		latch = getApplicationContext().getResources().openRawResourceFd(R.raw.latch);
		fastcar = getApplicationContext().getResources().openRawResourceFd(R.raw.fastcar);
		raise = getApplicationContext().getResources().openRawResourceFd(R.raw.raise);
		realworld = getApplicationContext().getResources().openRawResourceFd(R.raw.realworld);
		thinkin = getApplicationContext().getResources().openRawResourceFd(R.raw.thinkin);
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		mSensorManager.registerListener(mEventListenerOrient,mSensor, SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	@Override
	public void onStop(){
		mSensorManager.unregisterListener(mEventListenerOrient);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
