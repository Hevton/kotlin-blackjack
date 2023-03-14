package blackjack.domain

import blackjack.Shape
import blackjack.domain.state.DealerFirstTurn
import blackjack.domain.state.FirstTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class PlayerTest {
    @Test
    fun `이름과 상태 베팅금액 넘겨받아 플레이어를 생성한다`() {
        val name = "krrong"
        val card1 = Card(Shape.HEART, CardNumber.SIX)
        val card2 = Card(Shape.HEART, CardNumber.SEVEN)
        val state = FirstTurn(CardBunch(card1)).draw(card2)
        assertDoesNotThrow { Player(name, state, 1000) }
    }

    @Test
    fun `카드를 받아서 cardBunch에 추가한다`() {
        val name = "krrong"
        val card1 = Card(Shape.HEART, CardNumber.SIX)
        val card2 = Card(Shape.HEART, CardNumber.SEVEN)
        val state = FirstTurn(CardBunch(card1)).draw(card2)

        val player = Player(name, state, 1000)
        val card3 = Card(Shape.HEART, CardNumber.NINE)
        player.receiveCard(card3)

        assertThat(player.state.hand.cards.size).isEqualTo(3)
    }

    @Test
    fun `블랙잭으로 이겼을때 반의 상금을 받는다`() {
        val name = "krrong"
        val card1 = Card(Shape.HEART, CardNumber.JACK)
        val card2 = Card(Shape.HEART, CardNumber.ACE)
        val state = FirstTurn(CardBunch(card1)).draw(card2)
        val player = Player(name, state, 1000)

        val card3 = Card(Shape.HEART, CardNumber.NINE)
        val card4 = Card(Shape.HEART, CardNumber.KING)
        val state2 = DealerFirstTurn(CardBunch(card3)).draw(card4)
        val dealer = Dealer(state2)

        assertThat(player.getPrizeMoney(dealer)).isEqualTo(500)
    }

    @Test
    fun `플레이어가 이겼을대 1배의 상금을 돌려받는다`() {
        val name = "krrong"
        val card1 = Card(Shape.HEART, CardNumber.NINE)
        val card2 = Card(Shape.HEART, CardNumber.JACK)
        val state = FirstTurn(CardBunch(card1)).draw(card2)
        val player = Player(name, state, 1000)

        val card3 = Card(Shape.HEART, CardNumber.TWO)
        val card4 = Card(Shape.HEART, CardNumber.KING)
        val card5 = Card(Shape.HEART, CardNumber.QUEEN)
        val state2 = DealerFirstTurn(CardBunch(card3)).draw(card4).draw(card5)
        val dealer = Dealer(state2)

        assertThat(player.getPrizeMoney(dealer)).isEqualTo(1000)
    }

    @Test
    fun `플레이어가 졌을떄 -1만큼 잃는다 `() {
        val name = "krrong"
        val card1 = Card(Shape.HEART, CardNumber.TWO)
        val card2 = Card(Shape.HEART, CardNumber.KING)
        val card3 = Card(Shape.HEART, CardNumber.QUEEN)
        val state = FirstTurn(CardBunch(card1)).draw(card2).draw(card3)
        val player = Player(name, state, 1000)

        val card4 = Card(Shape.HEART, CardNumber.NINE)
        val card5 = Card(Shape.HEART, CardNumber.JACK)
        val state2 = DealerFirstTurn(CardBunch(card4)).draw(card5)
        val dealer = Dealer(state2)

        assertThat(player.getPrizeMoney(dealer)).isEqualTo(-1000)
    }

    @Test
    fun `비길경우 아무것도 받지않는다`() {
        val name = "krrong"
        val card1 = Card(Shape.HEART, CardNumber.TWO)
        val card2 = Card(Shape.HEART, CardNumber.KING)
        val card3 = Card(Shape.HEART, CardNumber.QUEEN)
        val state = FirstTurn(CardBunch(card1)).draw(card2).draw(card3)
        val player = Player(name, state, 1000)

        val card4 = Card(Shape.HEART, CardNumber.TWO)
        val card5 = Card(Shape.HEART, CardNumber.JACK)
        val card6 = Card(Shape.HEART, CardNumber.QUEEN)
        val state2 = DealerFirstTurn(CardBunch(card4)).draw(card5).draw(card6)
        val dealer = Dealer(state2)

        assertThat(player.getPrizeMoney(dealer)).isEqualTo(0)
    }
}
