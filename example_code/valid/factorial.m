class a {
  int factorial(int n) {
    if (n < 2) {
      return 1;
    }
    return n * factorial(n - 1);
  }
  void main(string[] args) {
    var call := new<a>;
    var counter := 1;
    while (counter < 10) {
      call.factorial(counter).print();
      counter := counter + 1;
    }
  }
}
