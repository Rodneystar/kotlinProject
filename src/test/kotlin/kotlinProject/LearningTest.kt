package kotlinProject

import kotlin.test.Test


open class LearningTest {
    @Test
    fun testingReceiver() {
        var l = LearningClass()
        l.function1("ourString")
    }
}
class LearningClass

fun LearningClass.function1(s:String) {
    println(s)
}
