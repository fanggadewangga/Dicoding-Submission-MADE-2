package com.fangga.core.ui

import com.fangga.core.base.BaseDiffUtil
import com.fangga.core.domain.model.User

class UserDiffUtil(
    oldList: List<User>,
    newList: List<User>
): BaseDiffUtil<User, String?>(oldList,newList) {
    override fun User.getItemIdentifier(): String? = this.username
}