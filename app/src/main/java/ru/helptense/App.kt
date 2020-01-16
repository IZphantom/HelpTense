package ru.helptense

import android.app.Application
import android.os.StrictMode
import android.util.Log
import android.util.Xml
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import ru.helptense.model.VerbsModel
import java.io.IOException
import java.lang.Exception


class App : Application() {
    companion object {
        lateinit var instance: App
    }

    var client = OkHttpClient()

    override fun onCreate() {
        super.onCreate()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        instance = this
    }


    @Throws(IOException::class)
    private fun getPage(verb: String): String {
        val urlVerb = verb.trim().toLowerCase()
        val request: Request = Request.Builder()
            .url("http://helptense.ru/verb/$urlVerb/")
            .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }

    private fun normalize(page: String): String {
        val start = page.indexOf("<table class=\"table-verb\">")
        val end = page.indexOf("</table>") + "</table>".length
        if (start == -1) {
            return ""
        }
        val result = page.substring(start, end)
        return result
    }

    fun getVerbs(verb: String): VerbsModel {
        try {
            val page = normalize(getPage(verb))
            val result = VerbsModel()
            if (page == "") {
                result.found = false
                return result
            }

            Log.d("Bla", page)
            val parser: XmlPullParser = Xml.newPullParser()
            var current = 0
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(page.byteInputStream(), null)
            var eventType: Int = parser.getEventType()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "td") {
                    eventType = parser.next()
                    if (eventType == XmlPullParser.START_TAG && parser.name == "b") {
                        eventType = parser.next()
                        if (eventType == XmlPullParser.TEXT) {
                            Log.d("Bla", "" + current + " " + parser.text)
                            when (current) {
                                0 -> result.verb0 = parser.text
                                1 -> result.verb1 = parser.text
                                2 -> result.verb2 = parser.text
                                3 -> result.verb3 = parser.text
                                4 -> result.verb4 = parser.text
                                5 -> result.verb5 = parser.text
                                6 -> result.verb6 = parser.text
                                7 -> result.verb7 = parser.text
                                8 -> result.verb8 = parser.text
                            }
                            if (current == 8) {
                                break
                            }
                            current++
                        }
                    }
                }
                eventType = parser.next()
            }
            return result
        } catch (e: Exception) {
            val result = VerbsModel()
            result.found = false
            return result
        }
    }
}