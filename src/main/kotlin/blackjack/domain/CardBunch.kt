package blackjack.domain

import blackjack.const.MAX_SCORE_CONDITION

class CardBunch constructor(val cards: List<Card>) {

    constructor(vararg cards: Card) : this(cards.toList())

    fun getTotalScore(): Int {
        var result = cards.sumOf { it.cardNumber.value }
        if (result > MAX_SCORE_CONDITION) return result
        if (containsAce()) {
            result = getOptimizedResult(result)
        }
        return result
    }

    private fun getOptimizedResult(result: Int): Int {
        var optimizedResult = result
        if ((optimizedResult + ACE_SCORE_GAP) <= MAX_SCORE_CONDITION) optimizedResult += ACE_SCORE_GAP
        return optimizedResult
    }

    fun isBurst(): Boolean = getTotalScore() > MAX_SCORE_CONDITION

    fun isDealerBurst(): Boolean = getTotalScore() > DEALER_ADD_CARD_CONDITION

    private fun containsAce(): Boolean = cards.any { card -> card.isAce() }

    companion object {
        private const val ACE_SCORE_GAP = 10
        private const val DEALER_ADD_CARD_CONDITION = 16
    }
}
