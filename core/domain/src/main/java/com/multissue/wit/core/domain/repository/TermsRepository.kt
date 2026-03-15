package com.multissue.wit.core.domain.repository

import com.multissue.wit.core.domain.model.terms.TermItem

interface TermsRepository {
    suspend fun getActiveTerms(): Result<List<TermItem>>
    suspend fun agreeTerms(agreements: List<Pair<String, Boolean>>): Result<String>
}
