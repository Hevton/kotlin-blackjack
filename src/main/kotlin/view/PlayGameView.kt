package view

import domain.Dealer
import domain.Players
import domain.User

class PlayGameView {

    fun printPlayers(players: Players) {
        printNoticeSplitCard(players.users)
        printPlayerCard(players.dealer, players.users)
    }

    private fun printNoticeSplitCard(users: List<User>) {
        val names = users.joinToString(SEPARATOR) { it.name }
        println(NOTICE_SPLIT_CARD.format(names))
    }

    private fun printPlayerCard(dealer: Dealer, users: List<User>) {
        println(PLAYER_CARD.format(dealer.name, dealer.getCards().first()))
        users.forEach { user ->
            println(
                PLAYER_CARD.format(
                    user.name,
                    (user.getCards().map { it.toString() }).joinToString(SEPARATOR)
                )
            )
        }
    }

    fun printUserCard(user: User) {
        println(
            PLAYER_CARD.format(
                user.name,
                (user.getCards().map { it.toString() }).joinToString(SEPARATOR)
            )
        )
    }

    fun isOneMoreCard(user: User): Boolean {
        println(REQUEST_MORE_CARD.format(user.name))
        return when (readln()) {
            Answer.YES.value -> true
            Answer.NO.value -> false
            else -> isOneMoreCard(user)
        }
    }

    fun printDealerPickNewCard() = println(NOTICE_DEALER_PICK_NEW_CARD)

    companion object {
        private const val NOTICE_DEALER_PICK_NEW_CARD = "딜러는 16이하라 한장의 카드를 더 받았습니다."
        private const val NOTICE_SPLIT_CARD = "딜러와 %s에게 2장의 카드를 나누었습니다."
        private const val PLAYER_CARD = "%s: %s"
        private const val SEPARATOR = ", "
        private const val REQUEST_MORE_CARD = "%s는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)"
    }
}
