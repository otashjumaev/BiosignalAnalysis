package org.hiro.biosignals.viewmodel

import androidx.lifecycle.ViewModel
import org.hiro.biosignals.database.UserDao

class UserListViewModel(val db: UserDao) : ViewModel() {
    var dataList = db.getUsers()
}