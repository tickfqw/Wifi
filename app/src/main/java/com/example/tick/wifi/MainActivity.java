package com.example.tick.wifi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private ListView allNetWork;
    private Button scan;
    private Button start;
    private Button stop;
    private Button check;
    private WifiAdmin mWifiAdmin;
    // 扫描结果列表
    private List<ScanResult> list;
    private ScanResult mScanResult;
    private List<ScanResult> sb=new ArrayList<ScanResult>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiAdmin = new WifiAdmin(MainActivity.this);
        init();
    }
    public void init(){
        allNetWork = (ListView) findViewById(R.id.allNetWork);
        scan = (Button) findViewById(R.id.scan);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        check = (Button) findViewById(R.id.check);
        scan.setOnClickListener(new MyListener());
        start.setOnClickListener(new MyListener());
        stop.setOnClickListener(new MyListener());
        check.setOnClickListener(new MyListener());
    }
    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int sum = 0;
            int state = mWifiAdmin.checkState();
            String showstate = null;
            switch (state){
                case 1:
                    showstate = "关闭";
                    break;
                case 3:
                    showstate = "已打开";
                    break;
                default:
                    break;
            }
            switch (v.getId()) {
                case R.id.scan://扫描网络
                    if(state == 1){
                        Toast.makeText(MainActivity.this, "当前wifi状态为："+showstate+".请打开wifi", Toast.LENGTH_SHORT).show();
                    }else if(state == 3) {
                        getAllNetWorkList();
                    }
                    break;
                case R.id.start://打开Wifi
                    mWifiAdmin.openWifi();
                   /* for (int i=0;i<50000000000;i++){
                        sum = sum + i;
                    }*/
                    //Toast.makeText(MainActivity.this, "当前wifi状态为：" + showstate, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.stop://关闭Wifi
                    mWifiAdmin.closeWifi();
                   /* for (int i=0;i<50000000000;i++){
                        sum = sum + i;
                    }*/
                    //Toast.makeText(MainActivity.this, "当前wifi状态为："+showstate, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.check://Wifi状态
                    Toast.makeText(MainActivity.this, "当前wifi状态为："+showstate, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
    public void getAllNetWorkList(){
        // 每次点击扫描之前清空上一次的扫描结果
        if(sb!=null){
            sb=new ArrayList<ScanResult>();
        }
        //开始扫描网络
        mWifiAdmin.startScan();
        list=mWifiAdmin.getWifiList();
        WifiDAO wifiDAO = new WifiDAO(MainActivity.this);
        if(list!=null){
            for(int i=0;i<list.size();i++){
                //得到扫描结果
                mScanResult=list.get(i);
                Tb_wifi tb_wifi = new Tb_wifi(mScanResult.SSID,"lhbg2016");
                wifiDAO.add(tb_wifi);// 添加收入信息
                sb.add(mScanResult);
            }

            //Tb_wifi tb_wifi = new Tb_wifi(wifiDAO.getMaxId() + 1,"name","password");


            //allNetWork.setText("扫描到的wifi网络：\n"+sb.toString());
            WifiAdapter adapter=new WifiAdapter(this,R.layout.wifi_item,list);
            allNetWork.setAdapter(adapter);
            allNetWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this,"所选wifi为："+sb.get(position).SSID, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(R.mipmap.wifi);
                    //builder.setMessage(sb.get(position).SSID);
                    builder.setTitle("请输入密码");
                    View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
                    builder.setView(view1);

                    final TextView username = (TextView)view1.findViewById(R.id.username);
                    final EditText password = (EditText)view1.findViewById(R.id.password);

                    username.setText(sb.get(position).SSID);
                    final String tmp_name = sb.get(position).SSID;

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            WifiDAO wifiDAO1 = new WifiDAO(MainActivity.this);
                            if (wifiDAO1.find(tmp_name).getPassword().equals(password.getText().toString())) {
                                Toast.makeText(MainActivity.this, "连接成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "请输入正确的密码！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //    设置一个NegativeButton
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "negative: " + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            });
        }

    }

}
