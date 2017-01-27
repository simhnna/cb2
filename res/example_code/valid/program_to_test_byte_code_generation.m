class a {
  int member;
  a self;
  void foo() {
    var foo := new<int[], 100>;
    "should be 100";
    foo.size();
    foo.set(1, 100);
    "should be 100";
    foo.get(1);
    "should be 0";
    foo.get(2);
    member := 1;
    "should be one";
    member;
  }

  int getSumPlusOne(int a, int b) {
    return a + b + 1;
  }

  int bar(int i) {
    if (i < 0) {
      return bar(0);
    }
    return i - 1;
  }

  a returnThis() {
    this;
    return this;
  }
}

class main {
  void main(string[] args) {
    var m := null<a>;
    if (m == null<a>) {
      "true";
    } else {
      "false";
    }
    if (m != null<a>) {
      "false";
    } else {
      "true";
    }
    m := new<a>;
    m.member := 1;
    "should be 1";
    m.member;
    m.member := m.member + 1;
    "should be 2";
    m.member;
    if (m == null<a>) {
      "false";
    } else {
      "true";
    }
    if (m != null<a>) {
      "true";
    } else {
      "false";
    }
    if (true == true) {
      "true";
    } else {
      "false";
    }
    m.self := m;
    if (m.self.member != m.member) {
      "error";
    }
    m.print();
    m.bar(1).print();
    m.bar(0);
    "should be -1";
    m.bar(-100);
    if (m.bar(0) != -1) {
      "false".print();
    }
    "asdf".size();
    "asdf".print();
    if ("asdf".size() != 4) {
      "false".print();
    } else {
      "true".print();
    }
    if ("asdf".size() == 4) {
      "true".print();
    } else {
      "false".print();
    }
    if (true || false) {
      "yes1";
    } else {
      "no1";
    }

    if (false && false) {
      "no2";
    } else {
      "yes2";
    }
    if (true) {
      "yes3";
    }
    if (false) {
      "no4";
    }
    if (1 > 2) {
      "oh no!".print();
    } else {
      "whoohoo".print();
    }
    if (1 <= 2) {
      "whoohoo".print();
    } else {
      "oh no!".print();
    }
    if (1 >= 1) {
      "whoohoo".print();
    } else {
      "oh no!".print();
    }
    "should be 2";
    4 / 2;
    var a := 1;
    "should be 1";
    a;
    a := 2;
    "should be 2";
    a;
    if (1 < 2) {
      "yes!";
    } else {
      "no";
    }
    "should be 1";
    3 % 2;
    "should be 6";
    3 * 2;
    "should be 5";
    3 + 2;
    "should be my name";
    "Simon" + " " + "Hanna";
    if (true) {
    } else {
    }
    "print numbers from 1 to 10";
    var counter := 1;
    while (counter <= 10) {
      counter;
      counter := counter + 1;
    }
    "should be true";
    !false;
    "should be false";
    !true;

    "should be 1";
    true ? 1 : 2;
    "should be 2";
    false ? 1 : 2;

    "should be 3";
    m.getSumPlusOne(1, 1);

    {
    var a := 1;
    var b := 2;
    "should be 4";
    m.getSumPlusOne(a, b);
    }
    "should be 9";
    (1 + 2) * 3;
  }
}
