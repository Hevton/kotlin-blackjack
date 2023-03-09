package view

class GameView(private val input: Input, private val output: Output) {
    fun printDealerStatus(dealerCards: String) {
        output.print(MESSAGE_DEALER_NAME)
        output.println(dealerCards)
    }

    fun printPlayerStatus(playerName: String, playerCards: String) {
        output.print(playerName)
        output.print(MESSAGE_PLAYER_NAME)
        output.println(playerCards)
    }

    fun printInitialUsersStatus(playersNames: List<String>) {
        output.println(
            MESSAGE_USERS_STATUS.format(
                playersNames.joinToString(", ")
            )
        )
    }

    fun printWhetherMoreCard(name: String) {
        output.println(MESSAGE_MORE_CARD.format(name))
    }

    fun readWhetherMoreCard(): Boolean {
        val trimmedInput = input.readln().trim()
        require(trimmedInput == "y" || trimmedInput == "n") {
            MESSAGE_CONDITION_ERROR.format(trimmedInput)
        }
        return trimmedInput == "y"
    }

    fun printDealerMoreCard() {
        output.println(MESSAGE_DEALER_MORE_CARD)
    }

    companion object {
        private const val MESSAGE_DEALER_NAME = "딜러: "
        private const val MESSAGE_PLAYER_NAME = "카드: "
        private const val MESSAGE_USERS_STATUS = "딜러와 %s에게 2장의 나누었습니다."
        private const val MESSAGE_MORE_CARD = "%s는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)"
        private const val MESSAGE_DEALER_MORE_CARD = "딜러는 16이하라 한장의 카드를 더 받았습니다."
        private const val MESSAGE_CONDITION_ERROR = "y나 n을 입력하여야 합니다. 입력된 값 : %s"
    }
}
