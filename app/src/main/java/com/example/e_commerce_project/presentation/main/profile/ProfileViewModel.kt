package com.example.e_commerce_project.presentation.main.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.data.remote.dto.response.AddAddressRequest
import com.example.e_commerce_project.data.remote.dto.response.ChangePasswordRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromAddressesRequest
import com.example.e_commerce_project.data.remote.dto.response.EditProfileRequest
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    init {
        loadProfile()
    }

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile()
            is ProfileIntent.ShowAddAddressDialog -> showAddAddressDialog()
            is ProfileIntent.HideAddAddressDialog -> hideAddAddressDialog()
            is ProfileIntent.AddAddress -> addAddress(intent.title, intent.address)
            is ProfileIntent.DeleteAddress -> deleteAddress(intent.addressId)
            is ProfileIntent.ClearAllAddresses -> clearAllAddresses()
            is ProfileIntent.ShowEditProfile -> showEditProfile()
            is ProfileIntent.HideEditProfile -> hideEditProfile()
            is ProfileIntent.EditProfile -> editProfile(intent.name, intent.phone, intent.address)
            is ProfileIntent.ShowChangePassword -> showChangePassword()
            is ProfileIntent.HideChangePassword -> hideChangePassword()
            is ProfileIntent.ChangePassword -> changePassword(intent.password)
            is ProfileIntent.RefreshProfile -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                _uiState.value = ProfileUiState.Loading
                val userId = userPreferencesRepository.getUserId()
                Log.d("ProfileViewModel", "User ID: $userId")

                if (userId == null) {
                    _uiState.value = ProfileUiState.Error("User not logged in")
                    return@launch
                }

                // Load user profile and addresses
                val userResult =
                    userRepository.getUser(userId, "canerture") // Replace with actual store
                val addressesResult = userRepository.getAddresses(userId, "canerture")

                val user = userResult.getOrNull()
                val addresses = addressesResult.getOrNull() ?: emptyList()

                if (user != null) {
                    _uiState.value = ProfileUiState.Success(
                        user = user,
                        addresses = addresses
                    )
                } else {
                    _uiState.value = ProfileUiState.Empty("No profile data found")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error loading profile", e)
                _uiState.value = ProfileUiState.Error("Error loading profile: ${e.message}")
            }
        }
    }

    private fun showAddAddressDialog() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isAddingAddress = true)
        }
    }

    private fun hideAddAddressDialog() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isAddingAddress = false)
        }
    }

    private fun addAddress(title: String, address: String) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is ProfileUiState.Success) {
                    val userId = userPreferencesRepository.getUserId()!!

                    val result = userRepository.addAddress(
                        addAddressRequest = AddAddressRequest(
                            userId = userId,
                            title = title,
                            address = address
                        )
                    )

                    if (result.isSuccess) {
                        // Reload addresses to get updated list
                        val addressesResult = userRepository.getAddresses(userId, "canerture")
                        val addresses = addressesResult.getOrNull() ?: currentState.addresses

                        _uiState.value = currentState.copy(
                            addresses = addresses,
                            isAddingAddress = false
                        )
                    } else {
                        _uiState.value =
                            ProfileUiState.Error("Failed to add address: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error adding address", e)
                _uiState.value = ProfileUiState.Error("Error adding address: ${e.message}")
            }
        }
    }

    private fun deleteAddress(addressId: Int) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is ProfileUiState.Success) {
                    val userId = userPreferencesRepository.getUserId()!!

                    val result = userRepository.deleteFromAddresses(
                        deleteFromAddressesRequest = DeleteFromAddressesRequest(
                            userId = userId,
                            id = addressId
                        )
                    )

                    if (result.isSuccess) {
                        _uiState.value = currentState.copy(
                            addresses = currentState.addresses.filter { it.id != addressId }
                        )
                    } else {
                        _uiState.value =
                            ProfileUiState.Error("Failed to delete address: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error deleting address", e)
                _uiState.value = ProfileUiState.Error("Error deleting address: ${e.message}")
            }
        }
    }

    private fun clearAllAddresses() {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is ProfileUiState.Success) {
                    val userId = userPreferencesRepository.getUserId()!!

                    val result = userRepository.clearAddresses(
                        userId = userId
                    )

                    if (result.isSuccess) {
                        _uiState.value = currentState.copy(addresses = emptyList())
                    } else {
                        _uiState.value =
                            ProfileUiState.Error("Failed to clear addresses: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error clearing addresses", e)
                _uiState.value = ProfileUiState.Error("Error clearing addresses: ${e.message}")
            }
        }
    }

    private fun showEditProfile() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isEditingProfile = true)
        }
    }

    private fun hideEditProfile() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isEditingProfile = false)
        }
    }

    private fun editProfile(name: String, phone: String, address: String) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is ProfileUiState.Success) {
                    val userId = userPreferencesRepository.getUserId()!!

                    val result = userRepository.editProfile(
                        store = "canerture", // Replace with actual store
                        editProfileRequest = EditProfileRequest(
                            userId = userId,
                            name = name,
                            phone = phone,
                            address = address
                        )
                    )

                    if (result.isSuccess) {
                        val updatedUser = currentState.user.copy(
                            name = name,
                            phone = phone
                        )

                        _uiState.value = currentState.copy(
                            user = updatedUser,
                            isEditingProfile = false
                        )
                    } else {
                        _uiState.value =
                            ProfileUiState.Error("Failed to update profile: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error updating profile", e)
                _uiState.value = ProfileUiState.Error("Error updating profile: ${e.message}")
            }
        }
    }

    private fun showChangePassword() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isChangingPassword = true)
        }
    }

    private fun hideChangePassword() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            _uiState.value = currentState.copy(isChangingPassword = false)
        }
    }

    private fun changePassword(password: String) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is ProfileUiState.Success) {
                    val userId = userPreferencesRepository.getUserId()!!

                    val result = userRepository.changePassword(
                        store = "canerture", // Replace with actual store
                        changePasswordRequest = ChangePasswordRequest(
                            userId = userId,
                            password = password
                        )
                    )

                    if (result.isSuccess) {
                        _uiState.value = currentState.copy(isChangingPassword = false)
                        // You might want to show a success message or navigate
                    } else {
                        _uiState.value =
                            ProfileUiState.Error("Failed to change password: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error changing password", e)
                _uiState.value = ProfileUiState.Error("Error changing password: ${e.message}")
            }
        }
    }
}
