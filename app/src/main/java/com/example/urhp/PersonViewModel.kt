package com.example.urhp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PersonViewModel(application : Application) : AndroidViewModel(application) {

    private val parentJob = Job()
    private  val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository : PersonRepository

    val allPerson: List<Person>

    init {
        val personDao = PersonDatabase.getDatabase(application,scope).personDao()
        repository = PersonRepository(personDao)
        allPerson = repository.allPerson
    }

    fun insert(person: Person) = scope.launch(Dispatchers.IO) {
        repository.insert(person)
    }



    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
    val personDao = PersonDatabase.getDatabase(application,scope).personDao()
    fun getAllPersons() = personDao.getAll()


//    val personDao = PersonDatabase.getDatabase(application).personDao()
//

//
    fun insertAll(persons: List<Person>) = personDao.insertAll(persons)
}
