class A {
  void meh() {
    if (true || false) {
      "whoohoo".print();
    } else {
      "oh no!".print();
    }

    if (false && false) {
      var id := 1;
    } else {
      var id := "meh";
    }
  }

  void main(string[] args) {
    var a := new<A>;
    a.meh();
  }
}
