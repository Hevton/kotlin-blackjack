package blackjack.domain

import blackjack.domain.carddeck.CardDeck

class BlackJackGame(names: List<String>, private val cardDeck: CardDeck, getBettingAmount: (String) -> Int) {
    val participants: Participants = Participants(names, ParticipantGenerator(cardDeck::drawCard, getBettingAmount))

    fun progressPlayersAddCard(getDecision: (Player) -> Boolean, transferPlayerCard: (Player) -> Unit) {
        participants.players.value.forEach { progressEachPlayerAddCard(it, getDecision, transferPlayerCard) }
    }

    private fun progressEachPlayerAddCard(
        player: Player,
        getDecision: (Player) -> Boolean,
        transferPlayerCard: (Player) -> Unit,
    ) {
        while (!player.isOverCondition()) {
            if (!progressEachTurn(player, getDecision, transferPlayerCard)) return
        }
    }

    private fun progressEachTurn(
        player: Player,
        getDecision: (Player) -> Boolean,
        transferPlayerCard: (Player) -> Unit,
    ): Boolean {
        if (getDecision(player)) {
            positiveGetCard(player, transferPlayerCard)
            return true
        }
        transferPlayerCard(player)
        return false
    }

    private fun positiveGetCard(
        player: Player,
        transferPlayerCard: (Player) -> Unit,
    ) {
        player.receiveCard(cardDeck.drawCard())
        transferPlayerCard(player)
    }

    fun progressDealerAddCard(transferDealerCondition: (Boolean) -> Unit) {
        while (!participants.dealer.isOverCondition()) {
            participants.dealer.receiveCard(cardDeck.drawCard())
            transferDealerCondition(participants.dealer.isOverCondition())
        }
        transferDealerCondition(participants.dealer.isOverCondition())
    }
}
