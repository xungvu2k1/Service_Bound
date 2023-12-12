package com.example.service_bound

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicBoundService : Service() {
    // Binder given to clients.
    private var mBinder : MyBinder = MyBinder()

    private var mMediaPlayer : MediaPlayer? = null
    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class MyBinder : Binder(){
        // Return this instance of MusicBoundService so clients can call public methods.
        fun getMusicBoundService() : MusicBoundService{
            return this@MusicBoundService// Trả về service.
        }
    }

    fun startMusic(){ // được gọi ở component liên kết với bound service, ở đây là gọi  main activity
        if (mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.trentinhbanduoitinhyeu)
        } else {
            mMediaPlayer?.start()
        }
    }// cũng cần xử lý mediaPlayer khi bị destroy

    override fun onCreate() {
        super.onCreate()
        Log.e("MusicBoundService", "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MusicBoundService", "onDestroy")
        if (mMediaPlayer != null){
            mMediaPlayer?.release()
            mMediaPlayer = null

        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("MusicBoundService", "onUnbind")
        return super.onUnbind(intent)

    }

    override fun onBind(p0: Intent?): IBinder {
        Log.e("MusicBoundService", "onBind")
        return mBinder
    }



}