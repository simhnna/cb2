class foo {
    int fooMember;
    void bar() {
      fooMember := 1;
      "one";
      fooMember;
      this.fooMember  := this.fooMember + 1;
      fooMember  := fooMember + 1;
      "three";
      this.fooMember;
    }
    void main(string[] args) {
      new<foo>.bar();
    }
}
