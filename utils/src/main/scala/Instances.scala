package mpis3

import scala.quoted.*
import scala.reflect.ClassTag

object Instances {
  given arrayFromExpr[T](using Type[T], FromExpr[T], ClassTag[T]): FromExpr[Array[T]] = new FromExpr[Array[T]] {
    def unapply(x: Expr[Array[T]])(using quotes: Quotes) = {
      import quotes.reflect.*

      def get(x: Term): Option[Array[T]] = x match {
        case Inlined(
            Some(TypeIdent("ToExpr$")), 
            Nil, 
            Apply(Select(Ident("Array"), "apply"), list)) => 
          list match {
            case Nil =>
              Some(Array.empty[T])
            case x :: xs =>
              x.asExprOf[T].value.flatMap { head =>
                xs match {
                  case List(Typed(Inlined(_, _, xs), _)) => 
                    xs.asExprOf[Seq[T]].value.map { tail =>
                      (head +: tail).toArray
                    }
                  case other =>
                    None
                }
             }
          }
      
        case other => 
          None
      }

      get(x.asTerm)
    }
  }
}
