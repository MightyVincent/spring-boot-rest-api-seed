package com.company.project.utils

import java.util.*

object OptionalString {
    fun ofEmpty(str: String?): Optional<String> {
        return if (str == null || str == "")
            Optional.empty()
        else
            Optional.ofNullable(str)
    }
}
