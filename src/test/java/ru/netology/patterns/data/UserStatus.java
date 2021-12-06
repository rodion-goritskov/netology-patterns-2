package ru.netology.patterns.data;

import com.google.gson.annotations.SerializedName;

enum UserStatus {
    @SerializedName("active")
    ACTIVE,
    @SerializedName("blocked")
    BLOCKED
}
