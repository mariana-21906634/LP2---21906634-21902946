package pt.ulusofona.lp2.deisiGreatGame

import pt.ulusofona.lp2.deisiGreatGame.CommandType.*

enum class CommandType{GET,POST}

fun router() : (CommandType) -> (GameManager,List<String>) -> String?{
    return {commandType -> escolhaComando(commandType) }
}

fun escolhaComando(commandType: CommandType):(GameManager,List<String>) -> String?{
    when(commandType){
        GET -> return{game, values -> functionGet(game, values)}
        POST -> return{game, values -> functionGet(game, values)}
    }
}

fun functionGet(game:GameManager, list: List<String>): String?{
    when (list[0]) {
        "PLAYER" -> return player(game,list)
        "PLAYER_BY_LANGUAGE" -> return playerByLanguage(game,list[1])
        "POLYGLOTS" -> return polyglots(game)
        "MOST_USED_POSITIONS" -> return mostUsedPositions(game, list)
        "MOST_USED_ABYSSES" -> return mostUsedAbysses(game, list)
    }
    return null
}

fun player(game: GameManager,list: List<String>): String?{
    return ""
}

fun playerByLanguage(game: GameManager,list: String): String?{
    return null
}

fun polyglots(game: GameManager): String?{
    return null
}

fun mostUsedPositions(game: GameManager,list: List<String>): String?{
    return null
}

fun mostUsedAbysses(game: GameManager,list: List<String>): String?{
    return null
}


