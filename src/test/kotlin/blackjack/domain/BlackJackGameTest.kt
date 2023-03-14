package blackjack.domain

import blackjack.Shape.HEART
import blackjack.domain.carddeck.CardDeck
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackJackGameTest {

    @Test
    fun `유저두명에게 각각 카드를 한장씩 추가로 분배한다`() {
        val stubBettingAmountInput = StubBettingAmountInput()
        val stubInput = StubInput()
        val blackJackGame = BlackJackGame(
            listOf("krong", "dogpig"),
            StubCardDeck(
                mutableListOf(
                    // 딜러 초기화
                    Card(HEART, CardNumber.TWO),
                    Card(HEART, CardNumber.THREE),
                    // 플레이어 두명 초기화
                    // 첫번째 유저
                    Card(HEART, CardNumber.TWO),
                    Card(HEART, CardNumber.FOUR),
                    // 두번째 유저
                    Card(HEART, CardNumber.FIVE),
                    Card(HEART, CardNumber.SIX),
                    // 플레이어 별로 한장씩 추가
                    Card(HEART, CardNumber.FIVE),
                    Card(HEART, CardNumber.SIX),
                ),
            ),
        ) { stubBettingAmountInput.getBettingAmountInput() }
        blackJackGame.progressPlayersAddCard({ stubInput.stubGetDecision() }, {})
        assertThat(blackJackGame.participants.players.value[0].state.hand.cards).isEqualTo(
            listOf(
                Card(HEART, CardNumber.TWO),
                Card(HEART, CardNumber.FOUR),
                Card(HEART, CardNumber.FIVE),
            ),
        )
        assertThat(blackJackGame.participants.players.value[1].state.hand.cards).isEqualTo(
            listOf(
                Card(HEART, CardNumber.FIVE),
                Card(HEART, CardNumber.SIX),
                Card(HEART, CardNumber.SIX),
            ),
        )
    }

    @Test
    fun `딜러의 첫카드 2장의 합이 16 이하라면 카드한장을 더뽑는다`() {
        val stubBettingAmountInput = StubBettingAmountInput()
        val blackJackGame = BlackJackGame(
            listOf("krong", "dogpig"),
            StubCardDeck(
                mutableListOf(
                    // 딜러 초기화
                    Card(HEART, CardNumber.TWO),
                    Card(HEART, CardNumber.THREE),
                    // 플레이어 두명 초기화
                    // 첫번째 유저
                    Card(HEART, CardNumber.TWO),
                    Card(HEART, CardNumber.FOUR),
                    // 두번째 유저
                    Card(HEART, CardNumber.FIVE),
                    Card(HEART, CardNumber.SIX),
                    // 추가 딜러가 받는카드
                    Card(HEART, CardNumber.FIVE),
                    Card(HEART, CardNumber.SEVEN),
                ),
            ),
        ) { stubBettingAmountInput.getBettingAmountInput() }
        blackJackGame.progressDealerAddCard {}
        assertThat(blackJackGame.participants.dealer.state.hand.cards).isEqualTo(
            listOf(
                Card(HEART, CardNumber.TWO),
                Card(HEART, CardNumber.THREE),
                Card(HEART, CardNumber.FIVE),
                Card(HEART, CardNumber.SEVEN),
            ),
        )
    }

    @Test
    fun `딜러의 첫카드 2장의 합이 17 이상이라면 카드한장를 뽑지 않는다`() {
        val stubBettingAmountInput = StubBettingAmountInput()
        val blackJackGame = BlackJackGame(
            listOf("krong", "dogpig"),
            StubCardDeck(
                mutableListOf(
                    // 딜러 초기화
                    Card(HEART, CardNumber.JACK),
                    Card(HEART, CardNumber.SEVEN),
                    // 플레이어 두명 초기화
                    // 첫번째 유저
                    Card(HEART, CardNumber.TWO),
                    Card(HEART, CardNumber.FOUR),
                    // 두번째 유저
                    Card(HEART, CardNumber.FIVE),
                    Card(HEART, CardNumber.SIX),
                    // 추가 딜러가 받는카드
                    Card(HEART, CardNumber.FIVE),
                ),
            ),
        ) { stubBettingAmountInput.getBettingAmountInput() }
        blackJackGame.progressDealerAddCard {}
        assertThat(blackJackGame.participants.dealer.state.hand.cards).isEqualTo(
            listOf(
                Card(HEART, CardNumber.JACK),
                Card(HEART, CardNumber.SEVEN),
            ),
        )
    }
}

class StubCardDeck(private val cardDeck: MutableList<Card>) : CardDeck {

    override fun drawCard(): Card = cardDeck.removeFirst()
}

class StubInput() {
    private val decisions = mutableListOf(true, false, true, false)

    fun stubGetDecision(): Boolean = decisions.removeFirst()
}

class StubBettingAmountInput() {
    private val BettingAmounts = mutableListOf(1000, 2000, 3000, 4000)
    fun getBettingAmountInput(): Int = BettingAmounts.removeFirst()
}
