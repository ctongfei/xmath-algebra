package poly.algebra

import poly.algebra.specgroup._

/**
 * Typeclass for multiplicative semigroups.
 * An multiplicative semigroup is a semigroup with the binary operation `mul` (`*`).
 * @tparam X Type of element
 */
trait MultiplicativeSemigroup[@sp(di) X] {

  /** Returns the product of two elements. */
  def mul(x: X, y: X): X

  /** Computes the product ''x'' * ''x'' * ··· * ''x'' with ''x'' repeated for ''n'' times. */
  def ipow(x: X, n: Int): X = asSemigroupWithMul.combineN(x, n)

  /** Casts this structure as a symbol-agnostic semigroup. */
  def asSemigroupWithMul: Semigroup[X] = new Semigroup[X] {
    def op(x: X, y: X) = mul(x, y)
  }
}

object MultiplicativeSemigroup {

  /** Retrieves the implicit multiplicative semigroup associated with the specific type. */
  def apply[@sp(di) X](implicit S: MultiplicativeSemigroup[X]) = S

  /** Creates an multiplicative semigroup of the specific type using the `*` operation provided. */
  def create[@sp(di) X](f: (X, X) => X) = new MultiplicativeSemigroup[X] {
    def mul(x: X, y: X): X = f(x, y)
  }
}

