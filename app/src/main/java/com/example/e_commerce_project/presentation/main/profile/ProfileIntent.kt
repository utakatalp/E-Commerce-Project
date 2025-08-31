package com.example.e_commerce_project.presentation.main.profile

sealed interface ProfileIntent {
    // Address related intents
    object LoadProfile : ProfileIntent
    object ShowAddAddressDialog : ProfileIntent
    object HideAddAddressDialog : ProfileIntent
    data class AddAddress(val title: String, val address: String) : ProfileIntent
    data class DeleteAddress(val addressId: Int) : ProfileIntent
    object ClearAllAddresses : ProfileIntent

    // Profile editing intents
    object ShowEditProfile : ProfileIntent
    object HideEditProfile : ProfileIntent
    data class EditProfile(val name: String, val phone: String, val address: String) : ProfileIntent

    // Password change intents
    object ShowChangePassword : ProfileIntent
    object HideChangePassword : ProfileIntent
    data class ChangePassword(val password: String) : ProfileIntent

    object RefreshProfile : ProfileIntent
}
