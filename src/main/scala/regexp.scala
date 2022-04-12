package mpis3

/*
 * Based on https://scala-lms.github.io/tutorials/regex.html
 */

// direct lift from scala-lms
trait RegexpMatcher {
  /* search for regexp anywhere in text */
  def matchsearch(regexp: String, text: String): Boolean = {
    if (regexp(0) == '^')
      matchhere(regexp, 1, text, 0)
    else {
      var start = -1
      var found = false
      while (!found && start < text.length) {
        start += 1
        found = matchhere(regexp, 0, text, start)
      }
      found
    }
  }


  /* search for restart of regexp at start of text */
  def matchhere(regexp: String, restart: Int, text: String, start: Int): Boolean = {
    if (restart==regexp.length)
      true
    else if (regexp(restart)=='$' && restart+1==regexp.length)
      start==text.length
    else if (restart+1 < regexp.length && regexp(restart+1)=='*')
      matchstar(regexp(restart), regexp, restart+2, text, start)
    else if (start < text.length && matchchar(regexp(restart), text(start)))
      matchhere(regexp, restart+1, text, start+1)
    else false
  }

  /* search for c* followed by restart of regexp at start of text */
  def matchstar(c: Char, regexp: String, restart: Int, text: String, start: Int): Boolean = {
    var sstart = start
    var found = matchhere(regexp, restart, text, sstart)
    var failed = false
    while (!failed && !found && sstart < text.length) {
      failed = !matchchar(c, text(sstart))
      sstart += 1
      found = matchhere(regexp, restart, text, sstart)
    }
    !failed && found
  }

  def matchchar(c: Char, t: Char): Boolean = {
    c == '.' || c == t
  }
}

trait RegexpMatcherTestCases {
  def testmatch(regexp: String, text: String, expected: Boolean): Unit

  testmatch("^hello$", "hello", true)
  testmatch("^hello$", "hell", false)
  testmatch("hell", "hello", true);
  testmatch("hell", "hell", true);
  testmatch("hel*", "he", true);
  testmatch("hel*$", "hello", false);
  testmatch("hel*", "yo hello", true);
  testmatch("ab", "hello ab hello", true);
  testmatch("^ab", "hello ab hello", false);
  testmatch("a*b", "hello aab hello", true);
  testmatch("^ab*", "abcd", true);
  testmatch("^ab*", "a", true);
  testmatch("^ab*", "ac", true);
  testmatch("^ab*", "bac", false);
  testmatch("^ab*$", "ac", false);
}

class RegexpMatcherTest extends RegexpMatcher with RegexpMatcherTestCases {
  override def testmatch(regexp: String, text: String, expected: Boolean): Unit = {
    println(s"regexp[$regexp] should " + (if (!expected) "not " else "") + s"match [$text]")

    if (expected != matchsearch(regexp, text)) {
      throw new RuntimeException("Match fails")
    }
  }
}

import scala.quoted.*

trait StagedRegexpMatcher {
  /* search for regexp anywhere in text */
  def matchsearch(regexp: String, text: Expr[String])(using Quotes): Expr[Boolean] = {
    if (regexp(0) == '^')
      matchhere(regexp, 1, text, Expr(0))
    else '{
      var start = -1
      var found = false
      while (!found && start < ${text}.length) {
        start += 1
        found = ${ matchhere(regexp, 0, text, '{ start }) }
      }
      found
    }
  }

  /* search for restart of regexp at start of text */
  def matchhere(regexp: String, restart: Int, text: Expr[String], start: Expr[Int])(using Quotes): Expr[Boolean] = {
    if (restart==regexp.length) {
      Expr(true)
    } else if (regexp(restart)=='$' && restart+1==regexp.length) '{
      ${start} == ${text}.length
    } else if (restart+1 < regexp.length && regexp(restart+1)=='*') {
      matchstar(regexp(restart), regexp, restart+2, text, start)
    } else '{
      if (${start} < ${text}.length && ${ matchchar(regexp(restart), '{ $text.apply($start) }) }) ${
        matchhere(regexp, restart+1, text, '{ $start + 1 })
      } else {
        false
      }
    }
  }

  /* search for c* followed by restart of regexp at start of text */
  def matchstar(c: Char, regexp: String, restart: Int, text: Expr[String], start: Expr[Int])(using Quotes): Expr[Boolean] = '{
    var sstart = ${start}
    var found: Boolean = ${matchhere(regexp, restart, text, '{ sstart })}
    var failed = false

    while (!failed && !found && sstart < ${ text }.length) {
      failed = ! ${ matchchar(c, '{ $text.apply(sstart) }) }
      sstart += 1
      found = ${ matchhere(regexp, restart, text, '{ sstart }) }
    }

    !failed && found
  }

  def matchchar(c: Char, t: Expr[Char])(using Quotes): Expr[Boolean] = {
    def cmp(c: Expr[Char], t: Expr[Char]): Expr[Boolean] = '{
      $c == $t
    }

    if (c == '.') then Expr(true)
    else cmp(Expr(c), t)
    // or '{ ${ Expr(c) } == $t }
  }
}
