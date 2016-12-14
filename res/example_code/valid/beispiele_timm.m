class Hello {
  void main(string[] args) {
    "...world!".print();
  }
}

class Helloo {
  void main(string[] args) {
    "...world!";
  }
}

class Size {
  void main(string[] args) {
    "...world!".size().print();
  }
}

class m {
  void main(string[] args) {
    null<m>;
    null<m[]>;
    null<m[][]>;
  }
}

class me {
  void main(string[] args) {
    while(args.size() > -1) {
      "hallo death".print();
    }
  }
}

class FunWithInt {
  int int;
  void main(string[] args) {
    new<FunWithInt>.int.print();
  }
}

class FunWithInteger {
  int int;
  void main(string[] args) {
    var x := new<FunWithInteger>;
    x.get().int := 7;
    x.int.print();
  }
  FunWithInteger get() {
    return this;
  }
}
