package domain

import domain.card.Card
import domain.card.CardShape
import domain.card.CardValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DealerTest {

    @Test
    fun `2장의 합이 16이하일 경우, true(Hit) 반환한다`() {
        // given
        val dealer = Dealer(
            cards = Cards(
                listOf<Card>(
                    Card(CardShape.CLUBS, CardValue.FIVE),
                    Card(CardShape.DIAMONDS, CardValue.THREE),
                )
            ),
        )

        // when
        val actual = dealer.isHit()

        // then
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `2장의 합이 17이상인 경우, false(!Hit) 를 반환한다`() {
        // given
        val dealer = Dealer(
            cards = Cards(
                listOf<Card>(
                    Card(CardShape.CLUBS, CardValue.JACK),
                    Card(CardShape.DIAMONDS, CardValue.EIGHT),
                )
            ),
        )

        // when
        val actual = dealer.isHit()

        // then
        assertThat(actual).isEqualTo(false)
    }
}
