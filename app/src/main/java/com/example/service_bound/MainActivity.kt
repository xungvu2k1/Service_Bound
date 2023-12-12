package com.example.service_bound

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var mMusicBoundService : MusicBoundService? = null
    private var isServiceConnected : Boolean = false
    private var mServiceConnection : ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {//IBinder giúp thực hiện để giao tiếp với bound service
            // xác định khi nào kết nối thành công

            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            var myBinder = service as MusicBoundService.MyBinder
            //get service thông qua myBinder
            mMusicBoundService = myBinder.getMusicBoundService() // lúc này, đã lấy được 1 bound service để gọi được những hàm mà service cung cấp.
            mMusicBoundService?.startMusic()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {// gọi hàm này khi service bị chết đột ngột do thiếu tài nguyên,...
            // xác định khi nào kết nối thất công
            isServiceConnected = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnStartService: Button = findViewById(R.id.btn_start_service)
        var btnStopService: Button = findViewById(R.id.btn_stop_service)

        btnStartService.setOnClickListener {
            onClickStartService()
        }

        btnStopService.setOnClickListener {
            onClickStopService()
        }
    }

    private fun onClickStartService() {
        var intent : Intent = Intent(this, MusicBoundService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE) // khởi động service với lời gọi bindService
    }

    private fun onClickStopService() {
        /*
        stop service khi service đó connected thành công
        muốn hủy bound service thì phải tháo bỏ các ràng buộc
         */
        if (isServiceConnected){
            unbindService(mServiceConnection)
            isServiceConnected = false //
        }
    }


}