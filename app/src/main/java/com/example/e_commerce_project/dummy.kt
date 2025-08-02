// =====================================================
// MANUEL DEPENDENCY INJECTION - BASIT YAPI
// =====================================================
// Kotlin Coroutines
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Android & AndroidX
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

// ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

// LiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
// 1. Data Layer
data class User(
    val id: String,
    val name: String,
    val email: String
)

// 2. Repository Interface
interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: String): User?
}

// 3. Repository Implementation
class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.fetchUsers()
    }

    override suspend fun getUserById(id: String): User? {
        return apiService.fetchUserById(id)
    }
}

// 4. API Service Interface
interface ApiService {
    suspend fun fetchUsers(): List<User>
    suspend fun fetchUserById(id: String): User?
}

// 5. API Service Implementation
class ApiServiceImpl : ApiService {

    override suspend fun fetchUsers(): List<User> {
        // Simulated API call
        delay(1000)
        return listOf(
            User("1", "John Doe", "john@example.com"),
            User("2", "Jane Smith", "jane@example.com"),
            User("3", "Bob Johnson", "bob@example.com")
        )
    }

    override suspend fun fetchUserById(id: String): User? {
        delay(500)
        return fetchUsers().find { it.id == id }
    }
}

// 6. MANUEL DI CONTAINER
class DIContainer {

    // Singleton instances
    private var apiServiceInstance: ApiService? = null
    private var userRepositoryInstance: UserRepository? = null

    fun provideApiService(): ApiService {
        return apiServiceInstance ?: ApiServiceImpl().also {
            apiServiceInstance = it
        }
    }

    fun provideUserRepository(): UserRepository {
        return userRepositoryInstance ?: UserRepositoryImpl(
            provideApiService()
        ).also {
            userRepositoryInstance = it
        }
    }
}

// 7. MANUEL DI - APPLICATION CLASS
class ManualDIApplication : Application() {

    val diContainer = DIContainer()

    override fun onCreate() {
        super.onCreate()
        // Setup completed
    }
}

// 8. MANUEL DI - VIEWMODEL
class ManualUserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val userList = userRepository.getUsers()
                _users.value = userList

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun getUserById(id: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val user = userRepository.getUserById(id)
                user?.let {
                    _users.value = listOf(it)
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}

// 9. MANUEL DI - VIEWMODEL FACTORY
class ManualViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ManualUserViewModel::class.java -> {
                ManualUserViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

// 10. MANUEL DI - ACTIVITY
class ManualMainActivity : AppCompatActivity() {

    private lateinit var viewModel: ManualUserViewModel
    private lateinit var diContainer: DIContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manuel Dependency Setup
        setupDependencies()

        // Setup UI
        setupObservers()

        // Load data
        viewModel.loadUsers()
    }

    private fun setupDependencies() {
        // Get DI Container from Application
        val app = application as ManualDIApplication
        diContainer = app.diContainer

        // Create ViewModel manually
        val userRepository = diContainer.provideUserRepository()
        val factory = ManualViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory)[ManualUserViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.users.observe(this) { users ->
            Log.d("ManualDI", "Users loaded: ${users.size}")
            // Update UI with users
        }

        viewModel.loading.observe(this) { isLoading ->
            Log.d("ManualDI", "Loading: $isLoading")
            // Show/hide loading indicator
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Log.e("ManualDI", "Error: $it")
                // Show error message
            }
        }
    }
}
