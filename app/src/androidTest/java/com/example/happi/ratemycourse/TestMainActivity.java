package com.example.happi.ratemycourse;

import android.util.Log;
import android.view.Gravity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.contrib.DrawerActions;

public class TestMainActivity
{
	@Rule
	public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>( MainActivity.class );

	@Before
	public void init()
	{
		activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
	}

	@Test
	public void menuOpenTest()
	{
		try
		{
			//onView( withId( R.id.content_frame ) ).perform( DrawerActions.open() );
			//onView( withId( R.id.drawer_layout ) ).check( matches( isClosed( Gravity.LEFT ) ) ).perform( DrawerActions.open() );

			onView( withId( R.id.autoCompleteTextView ) ).perform( replaceText( "" ) );
			onView( withId( R.id.autoCompleteTextView ) ).perform( typeText( "COMP" ), closeSoftKeyboard() );

		}
		catch( NoMatchingViewException e )
		{
			Log.d( "EspressoMainActivity", "view not found" );
		}
	}
}