class a {
  int[] arr;
  void main(string[] args) {
    arr := new<int[], 10>;
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
