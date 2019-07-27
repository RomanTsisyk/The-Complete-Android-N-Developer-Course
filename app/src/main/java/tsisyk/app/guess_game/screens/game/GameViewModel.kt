package tsisyk.app.guess_game.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.mutableListOf as mutableListOf1


private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)


class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object {
        private const val DONE = 0L
        private const val COUNTDOWN_PANIC_SECONDS = 10L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer
    private val _currentTime = MutableLiveData<Long>()
    private val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString: LiveData<String> = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word


    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score


    private lateinit var wordList: MutableList<String>

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    init {
        resetList()
        nextWord()
        _score.value = 0

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventBuzz.value = BuzzType.GAME_OVER
                _eventGameFinish.value = true
            }
        }

        timer.start()
    }

    private fun resetList() {

        val isLang = Locale.getDefault().language

        wordList = when (isLang) {
            "en" -> mutableListEN
           "es" -> mutableListES
         /*    "de" -> mutableListDE
            "cz" -> mutableListCZ
            "ua" -> mutableListUA*/
            else -> mutableListEN
        }
        wordList.shuffle()
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        _eventBuzz.value = BuzzType.CORRECT
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    private val mutableListEN: MutableList<String>
        get() = mutableListOf1("The Godfather", "The Shawshank Redemption", "Pulp Fiction", "Star Wars", "The Dark Knight", "GoodFellas", "The Matrix ", "Schindler\'s List", "Indiana Jones", "Fight Club", "Saving Private Ryan", "Back to the Future", "Gladiator", "The Lord of the Rings", "Braveheart", "Inception", "Jaws ", "Titanic", "Jurassic Park", "Terminator", "Rocky ", "Akira", "Underground", "The Big Sleep", "The Graduate", "The Hustler", "Anatomy of a Murder", "Before Sunset", "X-Men", "Papillon", "Beauty and the Beast", "The Night of the Hunter", "Roman Holiday", "Castle in the Sky", "Notorious", "Pirates of the Caribbean", "A Fistful of Dollars", "Yip Man", "The Imitation Game", "The King's Speech", "Dog Day Afternoon", "Barry Lyndon", "The Truman Show", "Throne of Blood", "Harry Potter", "Monsters, Inc", "Guardians of the Galaxy", "Memories of Murder", "Groundhog Day", "The Battle of Algiers", "Goodfellas", "12 Angry Men", "Léon: The Professional", "Once Upon a Time in the West", "The Pianist", "The Green Mile")

    private val mutableListES: MutableList<String>
    get() = mutableListOf1("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
}



