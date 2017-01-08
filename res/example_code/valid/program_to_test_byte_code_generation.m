class a {
  void foo() {
    var foo := new<int[], 100>;
    foo.set(1, 100);
    foo.get(2);
  }

  int bar(int i) {
    return i - 1;
  }

  void main(string[] args) {
    var m := new<a>;
    m.print();
    m.bar(1).print();
  }
}
