package com.korpi.domain.services.validation

fun email(email: String): Pair<Boolean, String> {
    return (!email.contains("@") to "Email must contain '@'")
}

fun password(password: String): Pair<Boolean, String> {
    var msg = ""
    var valid = true
    if (password.length < 8) {
        valid = false
        msg += "Password must contain at least 8 letters"
    }
    if (!(0..9).map { password.contains(it.toString().toCharArray().first()) }.contains(true)) {
        valid = false
        msg += "\nPassword must contain at least one numeric or special character"
    }
    return valid to msg
}
