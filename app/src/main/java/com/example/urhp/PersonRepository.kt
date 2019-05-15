package com.example.urhp

import android.support.annotation.WorkerThread
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class PersonRepository(private val personDao: PersonDao) {
    val allPerson: List<Person> = personDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(person: Person){
        personDao.insert(person)
    }

    fun fetchJSON(pageNo: Int) : Person{
        val value = OkHttpClient()
        lateinit var person : Person
        val url = "https://reqres.in/api/users?page=$pageNo"
        val request: Request = Request.Builder().url(url).build()
        value.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response){
                if (response.isSuccessful) {
                    val myResponse = response.body()?.string()
                    myResponse?.let {
                        val jsonValue = JSONObject(it)
                        val data = jsonValue.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val personObject = data.getJSONObject(i)
                            val firstName = personObject.getString("first_name")
                            val lastName = personObject.getString("last_name")
                            val id = personObject.getInt("id")
                            val avatar = personObject.getString("avatar")
                            person = Person(id, firstName, lastName, avatar)
                        }
                    }
//                    runOnUiThread{
//                        list.sortBy {
//                            it.id
//                        }
//                    }


//                    val gson = GsonBuilder().create()
//                    val feed = gson.fromJson(myResponse, Feed::class.java)
//                    runOnUiThread {
//                        // Have a List<Data> object in mainactivity. When api call returns feed, add the new items to the object.
//                        // set adapter.feed with the newly updated object. call notifydatasetchanged
//                        list += feed.person
//                        list.sortBy {
//                            it.id
//                        }
//                    }

                }
            }
        })
        return person
    }
}