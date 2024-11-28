package dependencies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private val repository: MyRepository
) : ViewModel() {

    fun getHello(): String {
        return repository.hello()
    }

}