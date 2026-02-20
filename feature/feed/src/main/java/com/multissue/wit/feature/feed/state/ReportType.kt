package com.multissue.wit.feature.feed.state

enum class ReportType(val text: String) {
    SEXUAL_CONTENT("성적인 콘텐츠"),
    VIOLENT_OR_HATEFUL_CONTENT("폭력적 또는 혐오스러운 콘텐츠"),
    HATE_OR_ABUSIVE_CONTENT("증오 또는 학대하는 콘텐츠"),
    HARMFUL_OR_DANGEROUS_ACTS("유해하거나 위험한 행위"),
    SPAM_OR_MISLEADING("스팸 또는 혼동을 야기하는 콘텐츠")
}