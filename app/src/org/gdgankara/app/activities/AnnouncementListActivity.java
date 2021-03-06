package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.AnnouncementListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.utils.Util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class AnnouncementListActivity extends BaseActivity{
	
	private ArrayList<Announcement> announcement_list;
	private ListView announcement_listview;
	private AnnouncementListAdapter announcementlist_adapter;
	private TabListener tabListener;
	private int pressed_index;
	private int pressed_back_button;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(0, 0);
		pressed_index=this.getIntent().getExtras().getInt("index");
		pressed_back_button=0;
		setActivityTheme(Util.device_height);
		setContentView(R.layout.announcementlist);
		announcement_list=Util.AnnouncementList;
		setUpView();
		childItemsActive();
		tabAktif();
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			announcementlist_adapter.setPressed(-1);
			announcementlist_adapter.notifyDataSetChanged();
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}
	
	private void setUpView() {
		
		announcement_listview=(ListView)findViewById(R.id.announcementlist);
		announcementlist_adapter=new AnnouncementListAdapter(this,announcement_list, Util.device_height,Util.device_width,pressed_index);
		announcement_listview.setAdapter(announcementlist_adapter);
	}
	
	private void childItemsActive() {
		announcement_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startAnnouncementPage(arg2);
			}
			
		});
	}


	private void startAnnouncementPage(int index) {
		pressed_back_button=1;
		if(announcement_list.get(index).getId()==1321){
			startSponsorPage();
		}
		else if(announcement_list.get(index).isSession()){
			startSessionPage(announcement_list.get(index).getSessionId());
		}
		else{
			Intent intent=new Intent(this,AnnouncementPageActivity.class);
			Bundle b=new Bundle();
			Long id=announcement_list.get(index).getId();
			b.putLong("id", id);
			intent.putExtras(b);
			this.startActivity(intent);
		}
		
	}
	
	private void startSponsorPage(){
		Intent intent=new Intent(this,SponsorListActivity.class);
		this.startActivity(intent);
	}
	
	private void startSessionPage(Long session_id){
		Intent intent=new Intent(this,SessionPageActivity.class);
		Bundle b=new Bundle();
		Long id=session_id;
		b.putLong("id", id);
		intent.putExtras(b);
		this.startActivity(intent);
	}
	
	private void setActivityTheme(int height){
		
		
		if(height<=320){
			setTheme(R.style.tagList_low);
		}
		else if(height<=480){
			setTheme(R.style.tagList_Medium);
		}
		else if(height<=800){
			setTheme(R.style.tagList_High);
		}
		else{
			setTheme(R.style.tagList_XHigh);
		}
		
	}

}
