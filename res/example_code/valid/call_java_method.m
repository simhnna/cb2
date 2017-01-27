class a {
  string getenv(string s) := java.lang.System.getenv;

  void main(string[] args) {
    var a := new<a>;
    a.getenv("HOME").print();
  }
}
