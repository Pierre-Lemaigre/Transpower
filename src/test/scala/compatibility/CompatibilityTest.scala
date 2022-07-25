package org.bestcolis
package compatibility

import org.scalatest.wordspec.AnyWordSpec

object FakeConstraints extends ConstraintsCompatibility[Int, Int] {
  val checkers: List[(Int, Int) => Boolean] = List(checkAValue, checkBValue, checkBothValue)

  override def checkedAgainst: List[(Int, Int) => Boolean] = checkers

  def checkAValue(a: Int, _b: Int): Boolean = a == 5

  def checkBValue(_a: Int, b: Int): Boolean = b == 10

  def checkBothValue(a: Int, b: Int): Boolean = a == 5 && b == 10
}

class CompatibilityTest extends AnyWordSpec {
  "Compatibility Constraints" should {
    "Return Nonexistent on 0 constraints respected" in {
      assertResult(Nonexistent) {
        Compatibility.compatibilityLevel(FakeConstraints, 3, 2)
      }
    }

    "Different than Nonexistent" in {
      val compatibility = Compatibility.compatibilityLevel(FakeConstraints, 5, 9)
      assert(compatibility != Nonexistent)
    }

    "Return Partial on 1 constraint respected" in {
      assertResult(Partial) {
        Compatibility.compatibilityLevel(FakeConstraints, 5, 2)
      }
    }

    "Different than Partial" in {
      val compatibility = Compatibility.compatibilityLevel(FakeConstraints, 5, 10)
      assert(compatibility != Partial)
    }

    "Return Complete on all constraints respected" in {
      assertResult(Complete) {
        Compatibility.compatibilityLevel(FakeConstraints, 5, 10)
      }
    }

    "Different than Complete" in {
      val compatibility = Compatibility.compatibilityLevel(FakeConstraints, 5, 9)
      assert(compatibility != Complete)
    }
  }
}
