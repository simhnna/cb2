class A {
  void meh() {
    var i := -foo();
  }
  int foo() {
    return 1 + (-2);
  }
  int rec(int i) {
    if (i < 0) { return 0;}
    else { return i + rec(i);}
  }
}
