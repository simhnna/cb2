class foo {
    int fooMember;
    void main(string[] args) {

    }
}
class bar {
  void barMethod(foo fooInstance) {
  	fooInstance.fooMember.print();
  }
  void main(string[] args) {
    var foo := new<foo>;
    var bar := new<bar>;
    bar.barMethod(foo);
  }
}
