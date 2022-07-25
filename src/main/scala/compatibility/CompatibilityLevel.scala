package org.bestcolis
package compatibility

import model.*

// TODO : Documentation
sealed trait CompatibilityLevel {
  val order: Int
}

case object Complete extends CompatibilityLevel {
  override val order = 1

  override def toString: String = "Complete"
}

case object Partial extends CompatibilityLevel {
  override val order = 2

  override def toString: String = "Partial"
}

case object Nonexistent extends CompatibilityLevel {
  override val order: Int = 3

  override def toString: String = "Nonexistent"
}

trait ConstraintsCompatibility[A, B] {
  def checkedAgainst: List[(A, B) => Boolean]
}

object Compatibility {
  def compatibilityLevel[A, B](constraints: ConstraintsCompatibility[A, B], checkedClass: A, constraintClass: B, notationFunction: List[Boolean] => Int): CompatibilityLevel = {
    val NumberOfConstraints = constraints.checkedAgainst.size
    val constraintsResults = constraints.checkedAgainst.map(constraint => constraint(checkedClass, constraintClass))
    val constraintsRating = notationFunction(constraintsResults)
    constraintsRating match
      case 0 => Nonexistent
      case NumberOfConstraints => Complete
      case _ => Partial
  }

  def compatibilityLevel[A, B](constraints: ConstraintsCompatibility[A, B], checkedClass: A, constraintClass: B): CompatibilityLevel = {
    val notationFunction = (constraintsResult: List[Boolean]) => constraintsResult.foldLeft(0) { (acc, v) => if (v) acc + 1 else acc }
    compatibilityLevel(constraints, checkedClass, constraintClass, notationFunction)
  }
}
