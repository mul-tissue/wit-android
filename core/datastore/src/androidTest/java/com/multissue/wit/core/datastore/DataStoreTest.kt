package com.multissue.wit.core.datastore

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multissue.wit.core.datastore.storage.Storage
import com.multissue.wit.core.datastore.storage.WitDataStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var storage: WitDataStore
    lateinit var testScope: TestScope

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        hiltRule.inject()
        testScope = TestScope(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }

    @Test
    fun `문자열_타입_데이터_저장_테스트`() = runTest {
        // Given
        val nameKey = Storage.Key.StringKey("user_name", "Unknown")
        val testName = "Wit User"

        // When
        storage.writeValue(nameKey, testName)

        // Then
        val result = storage.get(nameKey)
        assertEquals(testName, result)
    }

    @Test
    fun `클래스_타입_데이터_저장_테스트`() = runTest {
        // Given
        val userKey = Storage.Key.DataKey("user_data", userSerializer, null)
        val testUser = User("Wit User", 30)

        // When
        storage.writeValue(userKey, testUser)

        // Then
        val result = storage.get(userKey)
        assertEquals(testUser, result)
    }

    @Test
    fun `데이터_초기화_테스트`() = runTest {
        // Given
        val countKey = Storage.Key.IntKey("counter", 0)
        storage.writeValue(countKey, 100)

        // When
        storage.clearValue(countKey)

        // Then
        val result = storage.get(countKey)
        assertEquals(0, result)
    }
}