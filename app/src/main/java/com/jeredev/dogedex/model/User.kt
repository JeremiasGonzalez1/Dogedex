package com.jeredev.dogedex.model

import android.app.Activity
import android.content.Context

class User(
    val id: Long,
    val email: String,
    val token: String
) {
    companion object {

        private const val AUTH_SHARED_PREFERENCES = "auth_shared_preferences"
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val AUTH_TOKEN_KEY = "auth_token"
        fun setLoggedInUser(activity: Activity, user: User) {
            activity.getSharedPreferences(AUTH_SHARED_PREFERENCES, Context.MODE_PRIVATE).also {
                it.edit()
                    .putLong(ID_KEY, user.id)
                    .putString(EMAIL_KEY, user.email)
                    .putString(AUTH_TOKEN_KEY, user.token)
                    .apply()
            }
        }

        fun getLoggedInUser(activity: Activity): User? {
            val sharedPreferences =
                activity.getSharedPreferences(AUTH_SHARED_PREFERENCES, Context.MODE_PRIVATE)
                    ?: return null

            val userID = sharedPreferences.getLong(ID_KEY, 0)

            if (userID == 0L) return null

            return User(
                userID, email = sharedPreferences.getString(
                    EMAIL_KEY, ""
                ) ?: "", token = sharedPreferences.getString(AUTH_TOKEN_KEY, "") ?: ""
            )
        }
    }
}