package com.example.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket

class MainActivity : AppCompatActivity() {
    val SocketClient=SocketThread()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SocketClient.start()
        btm1.visibility=View.GONE
        Thread{
                while(true)
                {
                    Thread.sleep(1000)
                    if(!SocketClient.Connecting)
                    {
                        break
                    }
                }
            if(SocketClient.Notfail)
            {
                runOnUiThread {
                    btm1.visibility=View.VISIBLE
                    Toast.makeText(applicationContext,"連線成功",Toast.LENGTH_LONG).show()
                }

            }
            else
            {
                runOnUiThread{Toast.makeText(applicationContext,"連線失敗! 有可能是伺服器未開啟或者網路不穩,請重新嘗試!",Toast.LENGTH_LONG).show()}
            }
        }.start()
    }

    fun click(view: View)
    {

        if(SocketClient.Notfail)
        {
            Toast.makeText(applicationContext,"已成功傳送車牌號碼,請至出口稍等",Toast.LENGTH_LONG).show()
        }
        Thread{
            SocketClient.sendCar(editText.text.toString())
        }.start()
        }
    }


class SocketThread():Thread()
{
    var writer : PrintWriter? = null
    var Notfail:Boolean=false
    var Connecting:Boolean=true
    override fun run()
    {
        try {
            val s= Socket("192.168.43.201",5005)
            val output=s.getOutputStream()
            writer= PrintWriter(output,true)
            Notfail=true
        }
        catch(e:Exception)
        {

        }
        finally
        {
            Connecting=false
        }
    }

    fun sendCar(car:String)
    {
        writer?.println(car)
    }


}
