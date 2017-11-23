package com.example.user.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class clock_on extends Fragment implements LocationListener, OnMapReadyCallback {
    static final int MIN_TIME=1;
    static final float MIN_DIST=0;
    LocationManager mgr;
    WifiManager mWifiManager;
    WifiInfo mWifiinfo;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;


    private GoogleMap map;
    String member_id,out_flag;
    LatLng currPoint;
    TextView txv;
    Button btn;
    ImageButton mylocation;
    int flag=0;

    public clock_on(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clock_on, container, false);
        member_id = this.getArguments().getString("id");
        /*GPS服務*/
        mgr = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        txv = (TextView) view.findViewById(R.id.txv);
        btn = (Button) view.findViewById(R.id.btn);
        mylocation = (ImageButton) view.findViewById(R.id.mylocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkPermission();

        //首先取得Wi-Fi服務控制Manager
        mWifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        /*判斷wifi是否有開啟*/
        if(mWifiManager.isWifiEnabled()){
            mWifiManager.startScan();
        }
        else{
            mWifiManager.setWifiEnabled(true);
            Toast.makeText(getActivity(), "Wi-Fi開啟中", Toast.LENGTH_SHORT).show();
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onResume(){
        super.onResume();
        enableLocationUpdates(true);
    }

    @Override
    public void  onPause(){
        super.onPause();

        enableLocationUpdates(false);
    }
    Marker marker;

    public String getIp(){
        /*取得wifi連線資訊*/
        mWifiinfo = mWifiManager.getConnectionInfo();
        /*目前wifi連線ip還未轉正常數字*/
        int ipAddress = mWifiinfo.getIpAddress();
        int ipAddress1 = ipAddress & 0xff;
        int ipAddress2 = ipAddress >> 8 & 0xff;
        int ipAddress3 = ipAddress >> 16 & 0xff;
        int ipAddress4 = ipAddress >> 24 & 0xff;
        /*利用位移運算和AND運算計算IP*/
        String ip = String.format("%d.%d.%d.%d",(ipAddress1),(ipAddress2),(ipAddress3),(ipAddress4));
        return ip;
    }

    public String checkIp(){
        String check="";
        /*取得wifi連線資訊*/
        mWifiinfo = mWifiManager.getConnectionInfo();
        /*目前wifi連線ip還未轉正常數字*/
        int ipAddress = mWifiinfo.getIpAddress();
        int ipAddress1 = ipAddress & 0xff;
        int ipAddress2 = ipAddress >> 8 & 0xff;
        int ipAddress3 = ipAddress >> 16 & 0xff;
        int ipAddress4 = ipAddress >> 24 & 0xff;
        /*利用位移運算和AND運算計算IP*/
        /*目前wifi完整的ip*/
//        String ip = String.format("%d.%d.%d.%d",(ipAddress1),(ipAddress2),(ipAddress3),(ipAddress4));
        int c_ipAddress1 = 10;
        int c_ipAddress2 = 11;
        int c_ipAddress3 = 0;
        int c_ipAddress4 = 0;
        for(int i=1;i<=255;i++) {
            c_ipAddress3=i;
            for(int j=1;j<=255;j++){
                c_ipAddress4=j;
                if (ipAddress1==c_ipAddress1 && ipAddress2==c_ipAddress2 && ipAddress3==c_ipAddress3 && ipAddress4==c_ipAddress4){
                    check="YES";
                }
            }
        }
        /*判斷目標wifi完整的ip*/
        //                String c_ip = String.format("%d.%d.%d.%d",(c_ipAddress1),(c_ipAddress2),(c_ipAddress3),(c_ipAddress4));
        return check;
    }

    @Override
    public void onLocationChanged(final Location location) {
        /*判斷wifi是否有開啟*/
        if(mWifiManager.isWifiEnabled()){
            mWifiManager.startScan();
        }
        else{
            mWifiManager.setWifiEnabled(true);
            Toast.makeText(getActivity(), "Wi-Fi開啟中", Toast.LENGTH_SHORT).show();
        }
        /*取得定位*/
        if(location !=null){
            txv.setText(
                    String.format("緯度:%.4f ,經度:%.4f ,(%s定位)",
                            location.getLatitude(),
                            location.getLongitude(),
                            location.getProvider()));
            currPoint = new LatLng(location.getLatitude(),location.getLongitude());
            if(map!=null){
                map.animateCamera(CameraUpdateFactory.newLatLng(currPoint));
                if(marker!=null) {
                    marker.remove();
                }
                marker = map.addMarker(new MarkerOptions().position(currPoint).title("目前位置"));
            }
        }
        else {
            txv.setText("暫時無法取得定位資訊.....");
        }
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(btn.getText()=="今日已打卡完畢"){
                    Toast.makeText(getActivity(), "今日已打卡完畢", Toast.LENGTH_SHORT).show();
                }
                else {
                /*限定GPS打卡範圍在此座標範圍內*/
                    if (location.getLatitude() > 24.136430 && location.getLatitude() < 24.138533 && location.getLongitude() > 120.607159 && location.getLongitude() < 120.610254) {
                        flag = 1;
                        Toast.makeText(getActivity(), "GPS驗證通過", Toast.LENGTH_SHORT).show();
                        if (checkIp() == "YES") {
                            flag = 2;
                            Toast.makeText(getActivity(), "Wi-Fi驗證通過", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Wi-Fi驗證失敗，" + "目前ip為:" + getIp(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "GPS驗證失敗，請確認位置", Toast.LENGTH_SHORT).show();
                    }
                    //                if(flag>0) {
                    out_flag = String.valueOf(flag);
                    new check_in(getActivity(), btn).execute(member_id, out_flag);
                    //                }
                }
                }
        });
        mylocation.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                map.animateCamera(CameraUpdateFactory.newLatLng(currPoint));
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults){
        if(requestCode==200){
            if (grantResults.length >= 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),"程式需要定位權限才能運作",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkPermission() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},200);
        }
    }
    private void enableLocationUpdates(boolean isTurnOn) {
        if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if(isTurnOn){
                isGPSEnabled=mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled=mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(!isGPSEnabled && !isNetworkEnabled){
                    Toast.makeText(getActivity(),"請確認已開啟定位功能!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(),"取得資訊中...",Toast.LENGTH_LONG).show();

                    if(isGPSEnabled)
                        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,this);

                    if(isNetworkEnabled)
                        mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,MIN_DIST,this);
                }
            }
            else{
                mgr.removeUpdates(this);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.moveCamera(CameraUpdateFactory.zoomTo(18));
    }
}
