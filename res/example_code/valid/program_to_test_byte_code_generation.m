class a {
  void foo() {
    var foo := new<int[], 100>;
    "should be 100";
    foo.size();
    foo.set(1, 100);
    foo.get(2);
  }

  int bar(int i) {
    return i - 1;
  }
}

class main {
  void main(string[] args) {
    var m := new<a>;
    m.print();
    m.bar(1).print();
    m.bar(0);
    if (m.bar(0) != -1) {
      "false".print();
    }
    "asdf".size();
    "asdf".print();
    if ("asdf".size() != 4) {
      "false".print();
    } else {
      "true".print();
    }
    if ("asdf".size() == 4) {
      "true".print();
    } else {
      "false".print();
    }
  }
}
