package blackjack.domain

import blackjack.Shape
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardBunchTest {

    @Test
    fun `카드를 추가한다`() {
        val card1 = Card(Shape.HEART, CardNumber.SEVEN)
        val card2 = Card(Shape.HEART, CardNumber.SIX)
        val card3 = Card(Shape.HEART, CardNumber.NINE)

        val cardBunch = CardBunch(card1, card2, card3)

        assertThat(cardBunch.cards.size).isEqualTo(3)
    }

    @Test
    fun `6,7,9 카드의 총합을 계산하면 22가 되어야한다`() {
        val card1 = Card(Shape.HEART, CardNumber.SEVEN)
        val card2 = Card(Shape.HEART, CardNumber.SIX)
        val card3 = Card(Shape.HEART, CardNumber.NINE)

        val cardBunch = CardBunch(card1, card2, card3)

        val totalScore = cardBunch.getTotalScore()
        assertThat(totalScore).isEqualTo(22)
    }

    @Test
    fun `Ace,2,3 카드의 총합을 계산하면 16이 되어야한다`() {
        val card1 = Card(Shape.HEART, CardNumber.ACE)
        val card2 = Card(Shape.HEART, CardNumber.TWO)
        val card3 = Card(Shape.HEART, CardNumber.THREE)

        val cardBunch = CardBunch(card1, card2, card3)

        val totalScore = cardBunch.getTotalScore()
        assertThat(totalScore).isEqualTo(16)
    }

    @Test
    fun `Ace,Jack,King 카드의 총합을 계산하면 21이 되어야한다`() {
        val card1 = Card(Shape.HEART, CardNumber.ACE)
        val card2 = Card(Shape.HEART, CardNumber.JACK)
        val card3 = Card(Shape.HEART, CardNumber.KING)

        val cardBunch = CardBunch(card1, card2, card3)

        val totalScore = cardBunch.getTotalScore()
        assertThat(totalScore).isEqualTo(21)
    }

    @Test
    fun `카드의 총합이 21이 넘었다면 해당 카드 뭉치느 Burst된다`() {
        val card1 = Card(Shape.HEART, CardNumber.ACE)
        val card2 = Card(Shape.HEART, CardNumber.JACK)
        val card3 = Card(Shape.HEART, CardNumber.KING)
        val card4 = Card(Shape.HEART, CardNumber.TWO)

        val cardBunch = CardBunch(card1, card2, card3, card4)

        assertThat(cardBunch.isBurst()).isTrue
    }

    @Test
    fun `카드의 총합이 21이 넘지 않았다면 해당 카드뭉치는 Burst되지않는다`() {
        val card1 = Card(Shape.HEART, CardNumber.ACE)
        val card2 = Card(Shape.HEART, CardNumber.JACK)
        val card3 = Card(Shape.HEART, CardNumber.KING)

        val cardBunch = CardBunch(card1, card2, card3)

        assertThat(cardBunch.isBurst()).isFalse
    }
}
