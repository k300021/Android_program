package MyGoogleAPI.GoogleMap;

import java.util.ArrayList; 
import android.graphics.Canvas; 
import android.graphics.drawable.Drawable; 
import com.google.android.maps.ItemizedOverlay; 
import com.google.android.maps.MapView; 
import com.google.android.maps.OverlayItem; 
public class MapOverlay extends ItemizedOverlay<OverlayItem>{ 
	private ArrayList<OverlayItem> gList = new ArrayList<OverlayItem>(); 
	Drawable marker; 
	public MapOverlay(Drawable defaultMarker) { 
		super(defaultMarker); 
		marker = defaultMarker; 
	} 
	// 將OverlayItem加入gList中 
	public void addOverlayItem(OverlayItem oItem){ 
		gList.add(oItem); 
		populate(); 
	} 
	@Override 
	public void draw(Canvas canvas, MapView mapView, boolean shadow) { 
		super.draw(canvas, mapView, shadow); 
		boundCenterBottom(marker); 
	} 
	@Override 
	protected OverlayItem createItem(int arg0) { 
		return gList.get(arg0); 
	} 
	@Override public int size() {
		return gList.size(); 
	}
}