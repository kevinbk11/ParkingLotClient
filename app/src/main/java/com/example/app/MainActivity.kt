package com.example.app

import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.OutputStream
import java.io.PrintWriter
import java.lang.ref.WeakReference
import java.net.Socket
import java.nio.channels.SocketChannel
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    val SocketClient=SocketThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView1.text="請在下方輸入您的車牌(ex:AJV1688)"
        SocketClient.start()
    }

    fun click(view:View)
    {
        Thread{
            SocketClient.sendCar(editText.text.toString())
        }.start()
    }

}
class SocketThread():Thread()
{
    var writer : PrintWriter? = null

    override fun run()
    {
        val s=Socket("192.168.1.101",2333)
        val output=s.getOutputStream()
        writer= PrintWriter(output,true)
    }
    fun sendCar(car:String)
    {
        writer?.println(car)
    }
}
