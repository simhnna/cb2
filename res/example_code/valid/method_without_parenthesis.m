class a {
  void foo() {
    if (false) {
      foo;
    }
  }

  void main(string[] args) {
    var a := new <a>;
    a.foo;
  }
}
