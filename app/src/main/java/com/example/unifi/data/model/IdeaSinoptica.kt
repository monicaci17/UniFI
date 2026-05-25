package com.example.unifi.data.model

data class IdeaSinoptica(
    val titulo: String = "",
    val subideas: List<SubideaSinoptica> = emptyList()
)