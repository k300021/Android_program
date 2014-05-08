package MyGoogleAPI.GoogleMap;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

public class GoogleMapActivity extends MapActivity {
	private LinearLayout linearLayout;
	private MapView mapView;
	private MapController controller;
	private GeoPoint location;
	private Drawable mdrawable;
	private MapOverlay mapOverlay;
    private OverlayItem overlayItem;
    private ZoomControls myZoom;
    private MyTimer_GPS timer;
    private static double lat = 120.997367*1E6;
    private static double lon = 24.787528*1E6;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        linearLayout = (LinearLayout)findViewById(R.id.zoomView);
        mapView = (MapView)findViewById(R.id.mapView);
        // GPS收值
        controller = mapView.getController();
        mapView.setTraffic(true);
        location = new GeoPoint((int)lon, (int)lat);
        // 將座標設到Google Map的正中心
        controller.setCenter(location);
        // 利用Android圖示表示目前收取到的座標
        mdrawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        mapOverlay = new MapOverlay(mdrawable);
        overlayItem = new OverlayItem(location, "","");
        mapOverlay.addOverlayItem(overlayItem);
        mapView.getOverlays().add(mapOverlay);
        
        myZoom = (ZoomControls)mapView.getZoomControls();
        linearLayout.addView(myZoom);
        
        timer = new MyTimer_GPS(86400000, 1000);
        timer.start();
        
    }
    
    public class MyTimer_GPS extends CountDownTimer implements LocationListener
	{
		LocationManager locMan = null;
		public MyTimer_GPS(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
			
			locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if(locMan.isProviderEnabled(LocationManager.GPS_PROVIDER))
					locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, this);
			else if(locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
				locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, this);
			else
			{
				Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		}
		
		public void onTick(long arg0)
		{
			// TODO Auto-generated method stub
			Location MyLoc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(MyLoc == null)MyLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if(MyLoc != null)
			{
				location = new GeoPoint((int)(MyLoc.getLatitude()*1E6), (int)(MyLoc.getLongitude()*1E6));
				controller.setCenter(location);
				overlayItem = new OverlayItem(location, "","");
		        mapOverlay.addOverlayItem(overlayItem);
		        mapView.getOverlays().add(mapOverlay);
			}
		}

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}