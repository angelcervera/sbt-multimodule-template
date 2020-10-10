package com.example.module1

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class Module1Hello extends AnyWordSpec with Matchers  {
  "testing response with name in submodule1" in {
    Hello.sayHello("Angel") shouldBe "Hello Angel"
  }
}
