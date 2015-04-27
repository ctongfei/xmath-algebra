package poly.algebra

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
trait TotalOrder[@specialized(Int, Double) X] extends Lattice[X] with WeakOrder[X] {
  def min(x: X, y: X): X = if (lt(x, y)) x else y
  def max(x: X, y: X): X = if (lt(x, y)) y else x
  def sup(x: X, y: X) = max(x, y)
  def inf(x: X, y: X) = min(x, y)

  override def le(x: X, y: X) = cmp(x, y) <= 0
  override def ge(x: X, y: X) = cmp(x, y) >= 0
}

object TotalOrder {

  def apply[@specialized(Int, Double) X](implicit O: TotalOrder[X]) = O

  def create[@specialized(Int, Double) X](fLt: (X, X) => Boolean) = new TotalOrder[X] {
    def cmp(x: X, y: X) = if (fLt(x, y)) -1 else if (x == y) 0 else 1
    override def lt(x: X, y: X) = fLt(x, y)
    override def gt(x: X, y: X) = fLt(y, x)
  }

  def on[@specialized(Int, Double) X, Y](f: Y => X)(implicit O: TotalOrder[X]) = new TotalOrder[Y] {
    def cmp(x: Y, y: Y) = O.cmp(f(x), f(y))
  }

}
