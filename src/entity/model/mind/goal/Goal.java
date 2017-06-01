package entity.model.mind.goal

import entity.model.Animal
import org.joml.Vector2f
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors

abstract class Goal(val animal: Animal) {
    private val preReqs: List<Goal>? = null
    private var next: Goal? = null

    abstract fun getMovement(delta: Float): Vector2f

    fun preReqs(): List<Goal> {
        return this.preReqs!!
                .filter { goal -> !goal.isCompleted }
    }

    abstract val isCompleted: Boolean

    fun next(next: Goal) {
        this.next = next
    }

    operator fun next(): Goal {
        return this.next!!
    }
}
