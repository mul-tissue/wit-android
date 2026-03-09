package com.multissue.wit.core.datastore.token

import com.multissue.wit.core.datastore.storage.Storage

object WitStorageKeys {
    val ACCESS_TOKEN = Storage.Key.StringKey("access_token", defaultValue = null)
    val REFRESH_TOKEN = Storage.Key.StringKey("refresh_token", defaultValue = null)
}
