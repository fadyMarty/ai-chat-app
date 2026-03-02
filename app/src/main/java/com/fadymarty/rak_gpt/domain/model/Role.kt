package com.fadymarty.rak_gpt.domain.model

import com.fadymarty.rak_gpt.data.data_source.remote.dto.RoleDto

enum class Role {
    USER,
    SYSTEM,
    ASSISTANT,
    FUNCTION
}

fun Role.toRoleDto(): RoleDto {
    return when (this) {
        Role.USER -> RoleDto.USER
        Role.SYSTEM -> RoleDto.SYSTEM
        Role.ASSISTANT -> RoleDto.ASSISTANT
        Role.FUNCTION -> RoleDto.FUNCTION
    }
}