package com.example.logonrm.demosoap

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class MainActivity : AppCompatActivity() {

    private val url = "http://10.3.2.42:8080/CalculadoraWSService/CalculadoraWS?wsdl";
    private val namespace = "http://heiderlopes.com.br/";
    private val methodName = "calcular";
    private val soapAction = namespace + methodName;
    private val parameter1 = "num1";
    private val parameter2 = "num2";
    private val parameter3 = "op";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        calcular.setOnClickListener({
            CallWebService().execute(numero1.text.toString(), numero2.text.toString(),spOperacao.getSelectedItem().toString())

        })
    }

    inner class CallWebService : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {

            var result = ""

            val soapObject = SoapObject(namespace, methodName)

            var number1Info = PropertyInfo()
            number1Info.name = parameter1
            number1Info.value = params[0]
            number1Info.type = Integer::class.java

            var number2Info = PropertyInfo()
            number2Info.name = parameter2
            number2Info.value = params[1]
            number2Info.type = Integer::class.java

            var number3Info = PropertyInfo()
            number3Info.name = parameter3
            number3Info.value = params[2]
            number3Info.type = String::class.java

            soapObject.addProperty(number1Info)
            soapObject.addProperty(number2Info)
            soapObject.addProperty(number3Info)

            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.setOutputSoapObject(soapObject)

            val httpTransporteSe = HttpTransportSE(url)

            try {

                httpTransporteSe.call(soapAction, envelope)
                val soapPrimitive = envelope.response
                result = soapPrimitive.toString()


            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result

        }

        override fun onPostExecute(result: String?) {

            resultado.text = result


        }

    }
}
