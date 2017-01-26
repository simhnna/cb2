class foobar {
// this is not allowed with classes, only with methods and flow control
  int foo() {{{{{{
  	return 1;
  }}}}}}

  void main(string[] args) {
    var a := new<foobar>;
    if (a.foo != 1) {
      "err";
    }
  }
}
