class Hello {
  void main(string[] args) {
    "...world!".print();
  }
}

class Hello {
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

class m {
  void main(string[] args) {
    while(args.length > -1) {
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

class FunWithInt {
  int int;
  void main(string[] args) {
    var x := new<FunWithInt>;
    x.get().int := 7;
    x.int.print();
  }
  FunWithInt get() {
    return this;
  }
}
