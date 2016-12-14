class a {
  void main(string[] args) {
    var arr := new<int[], 10>;
    var i := 0;
    while (i < 10) {
      arr.set(i, i);
      if (arr.get(i) != i) {
        "Got an error".print();
      }
      i := i + 1;
    }
  }
}
