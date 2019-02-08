package com.example.happi.ratemycourse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


/***
 * This is like a splash screen. It should load all the permissions and then the other activity
 *
 */
public class MainActivity extends AppCompatActivity implements
						NavigationView.OnNavigationItemSelectedListener
{
	public static final String LOG_TAG = "MainActivity";
	public static final int MY_PERMISSIONS_REQUEST_CODE = 123;

	public static final String DATA_MAIN_PAGE = "data_main_page";

	private FragmentManager _fragmentManager;
	private FragmentTransaction _fragmentTransaction;
	private DrawerLayout _navDrawerLayout;
	private NavigationView _navView;
	//private Toolbar _drawerToolbar;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		setupPermissions();
	}

	private void setupPermissions()
	{
		Log.d( LOG_TAG, "setting up permissions!" );
		String[] permissions = {
				Manifest.permission.INTERNET
		};

		boolean recheckPermissions = false;
		int ii= 0;
		while ( ii < permissions.length )
		{
			if ( ActivityCompat.checkSelfPermission( this, permissions[ii] ) != PackageManager.PERMISSION_GRANTED )
			{
				recheckPermissions = true;
				ii = permissions.length;
			}
			ii++;
		}

		if ( recheckPermissions )
		{
			Log.d( LOG_TAG, "permissions are missing!" );
			// Permission is not granted
			ActivityCompat.requestPermissions( this, permissions, MY_PERMISSIONS_REQUEST_CODE );
		}
		else
		{
			Log.d( LOG_TAG, "permissions are already granted!" );
			// load stuff here
			loadScreenElements();
			initFragments();
			initScreenElements();
		}
	}

	private void loadScreenElements()
	{
		_navView = findViewById( R.id.drawer_nav_view );
		_navDrawerLayout = findViewById( R.id.drawer_layout );
		//_drawerToolbar = findViewById( R.id.toolbar );

		_navView.setNavigationItemSelectedListener( this );

		// setup header bar for navigation drawer aka burger menu
		//setSupportActionBar( _drawerToolbar );
		//ActionBar actionbar = getSupportActionBar();
		//actionbar.setDisplayHomeAsUpEnabled( true );
		//actionbar.setHomeAsUpIndicator( R.drawable.ic_burger_menu );
	}

	private void initFragments()
	{
		_fragmentManager = getSupportFragmentManager();
	}

	/**
	 * This method is overridden for the burger menu click action
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch ( item.getItemId() )
		{
			case android.R.id.home:
				_navDrawerLayout.openDrawer( GravityCompat.START );
				return true;
		}
		return super.onOptionsItemSelected( item );
	}

	@Override
	public boolean onNavigationItemSelected( @NonNull MenuItem menuItem )
	{
		Log.d( LOG_TAG, "onNavigationItemSelected: " + menuItem );
		Log.d( LOG_TAG, "onNavigationItemSelected: " + menuItem.getItemId() + ", " + R.id.drawer_nav_mainPage + ", " + R.id.drawer_nav_login );

		menuItem.setChecked( true );
		_navDrawerLayout.closeDrawers();

		_fragmentTransaction = _fragmentManager.beginTransaction();

		switch ( menuItem.getItemId() )
		{
			case R.id.drawer_nav_mainPage:
				// load fragment
				MainPageFragment mainPageFragment = new MainPageFragment();
				_fragmentTransaction.replace( R.id.content_frame, mainPageFragment );
				//_fragmentTransaction.addToBackStack( BACK_STATE_CAM_FRAGMENT );
				break;
			case R.id.drawer_nav_login:
				LoginPageFragment loginPageFragment = new LoginPageFragment();
				_fragmentTransaction.replace( R.id.content_frame, loginPageFragment );
				//_fragmentTransaction.addToBackStack( BACK_STATE_CAM_FRAGMENT );
				break;
			case R.id.drawer_nav_viewAllCourses:
				break;
			case R.id.drawer_nav_instructions:
				break;
			case R.id.drawer_nav_aboutUs:
				break;
			default:
				Log.d( LOG_TAG, "invalid Navigation item selection" );
				break;
		}

		_fragmentTransaction.commit();

		return true;
	}

	private void initScreenElements()
	{
		_navView.setCheckedItem( R.id.drawer_nav_mainPage );
		onNavigationItemSelected( _navView.getMenu().getItem( 0 ) );
	}

	@Override
	public void onAttachFragment( Fragment fragment )
	{
		super.onAttachFragment( fragment );

		if ( fragment instanceof MainPageFragment )
		{
			// do something if needed
			//( ( MainPageFragment ) fragment).OnFragmentInteractionListener = this;
		}
	}
}
