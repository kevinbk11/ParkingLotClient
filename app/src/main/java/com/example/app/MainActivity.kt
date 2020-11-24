package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {

    val SocketClient=SocketThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView1.text="請在下方輸入您的車牌(ex:AJV1688)"
        SocketClient.start()

    }

    fun click(view: View)
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
        val s= Socket("192.168.43.201",5005)
        val output=s.getOutputStream()
        writer= PrintWriter(output,true)
    }
    fun sendCar(car:String)
    {
        writer?.println(car)
    }
}