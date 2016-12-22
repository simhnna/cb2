class a {
  int foo() {
    return 1;
  }
  void main(string[] args) {
    var m := new<a>;
    var int := m.foo();
    m.foo() := int;
  }
}
