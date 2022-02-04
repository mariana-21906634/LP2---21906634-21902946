package pt.ulusofona.lp2.deisiGreatGame

import pt.ulusofona.lp2.deisiGreatGame.CommandType.*
import java.util.stream.Collectors

enum class CommandType{GET,POST}

fun router() : (CommandType) -> (GameManager,List<String>) -> String?{
    return {commandType -> escolhaComando(commandType) }
}

fun escolhaComando(commandType: CommandType):(GameManager,List<String>) -> String?{
    when(commandType){
        GET -> return{game, values -> functionGet(game, values)}
        POST -> return{game, values -> functionPost(game, values) }
    }
}

fun functionGet(game:GameManager, list: List<String>): String?{
    when (list[0]) {
        "PLAYER" -> return player(game,list)
        "PLAYERS_BY_LANGUAGE" -> return playerByLanguage(game,list)
        "POLYGLOTS" -> return polyglots(game)
        "MOST_USED_POSITIONS" -> return mostUsedPositions(game, list)
        "MOST_USED_ABYSSES" -> return mostUsedAbysses(game, list)
    }
    return null
}

fun functionPost(game:GameManager, list: List<String>): String?{
    when (list[0]) {
        "MOVE" -> return player(game,list)
        "ABYSS" -> return playerByLanguage(game,list)
    }
    return null
}

fun player(game: GameManager,list: List<String>): String?{
    return game.programmers.values.stream().filter { p -> p.getName().contains(list[1])}.findAny().map(Programmer::name)
        .orElse("Inexistent player")
}

fun playerByLanguage(game: GameManager,list: List<String>): String?{
    return game.programmers.values.stream().filter { p -> p.getLinguagemFavorita().split("; ")
        .contains(list[1])}.map(Programmer :: name).collect(Collectors.joining(","))
}

fun polyglots(game: GameManager): String?{
    return game.programmers.values.stream().filter { p -> p.getLinguagemFavorita().split("; ")
        .count() > 1 }.sorted { o1, o2 -> o1.getLinguagemFavorita().split("; ").count().
    compareTo(o2.getLinguagemFavorita().split("; ").count()) }.map { a -> a.getName() +  ":" +
            a.getLinguagemFavorita().split("; ").count() + "\n"}.collect(Collectors.joining()).trim()
}

fun mostUsedPositions(game: GameManager,list: List<String>): String?{
    return null
}

fun mostUsedAbysses(game: GameManager,list: List<String>): String?{
    return null
}


