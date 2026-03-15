package com.multissue.wit.core.domain.usecase.terms

import com.multissue.wit.core.domain.model.terms.TermItem
import com.multissue.wit.core.domain.repository.TermsRepository
import javax.inject.Inject

class GetActiveTermsUseCase @Inject constructor(
    private val termsRepository: TermsRepository,
) {
    suspend operator fun invoke(): Result<List<TermItem>> =
        termsRepository.getActiveTerms()
}
