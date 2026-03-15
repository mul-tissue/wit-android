package com.multissue.wit.core.domain.usecase.terms

import com.multissue.wit.core.domain.repository.TermsRepository
import javax.inject.Inject

class AgreeTermsUseCase @Inject constructor(
    private val termsRepository: TermsRepository,
) {
    suspend operator fun invoke(agreements: List<Pair<String, Boolean>>): Result<String> =
        termsRepository.agreeTerms(agreements)
}
