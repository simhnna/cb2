class a {
  bool foo() {
    "foo";
    return true;
  }

  bool bar() {
    "bar";
    return false;
  }

  void main(string[] args) {
    var n := new<a>;
    if (n.bar() || n.foo()) {
      "printed bar and foo";
    } else {
      "error";
    }
    if (n.foo() && n.bar()) {
      "error";
    } else {
      "printed foo and bar";
    }

    if (n.foo() || n.bar()) {
      "printed foo only";
    } else {
      "error";
    }
    if (n.bar() && n.foo()) {
      "error";
    } else {
      "printed bar only";
    }
  }
}
