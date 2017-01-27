class math {
  int abs(int a) := java.lang.Math.abs;
  int addExact(int a, int b) := java.lang.Math.addExact;

  void main(string[] args) {
    var a := new<math>;
    if (a.abs(-10) != 10) {
      a.abs(-10);
    }
    if (a.addExact(10, -2) != 8) {
      a.addExact(10, -2);
    }
  }
}
