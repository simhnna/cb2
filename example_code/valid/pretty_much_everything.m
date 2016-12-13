class A {
  void firstFunction() {
    "whatever";
    var meh := true;
    meh := false;
  }
  int returnsConstant() {
    return 1;
  }
  int fibonacci(int n) {
    return n;
  }
  int returnArgument(int n) {
    (-n).print();
    return n;
  }
  int additiveInverse(int n) {
    return -n;
  }
  int identity(int n) {
    return ----n;
  }
  void allKindsOfIntOperators() {
    var x := 0;
    var y := 0;
    x := 1 * 2 / 3 + 4 - 5 % 6;
    y := 1 * (2 + 3);
    if (1 * 2 + 3 != 2 + 3 * 1) {
      "nooo!".print();
    }
  }
  int messedUpStatement() {
    return 1 - ---2 + -2 - 1;
  }
  int factorial(int n) {
    if (n <= 1) {
      return 1;
    }
    return factorial(n - 1) * n;
  }
  int otherFactorial(int n) {
    if (n < 1 || n == 1) {
      return 0 + 1;
    } else {
    }
    var result := n;
    result := result * factorial(n - 1);
    return result;
  }
  int nonRecursiveFactorial(int n) {
    {
    }
    var result := 1;
    while (n > 1) {
      result := result * n;
      n := n - 1;
    }
    return result;
  }
  void main(string[] args) {
    args.set(1, "bla");
    var test := args.get(1);
    var a := new <A>;
    a.nonRecursiveFactorial(1).print();
    a.nonRecursiveFactorial(a.otherFactorial(2)).print();
    args.size().print();
    var car := new <Car>;
    var num := 1;
    var cars := new <Car[][], 1, num>;
    car.name.print();
    car.name.print();
    car.uselessMethod(1, 2, 3);
    car.setName("whhhhaaat");
    if (car.getName() != "whhhhaaat" || car.name != car.getName()) {
      "Noooo!".print();
    }
    if (car.getName() == "whhhhaaat" && car.name == car.getName()) {
    }
    (1 >= 2).print();
    (!true).print();
    if (car.getName().size() != car.name.size()) {
      "Noo".print();
    } else {
      "Yeeees".print();
    }
    car.velocityTimesConstant(100).print();
    var nothing := null<Car>;
  }
}
class Car {
  string name;
  int maximumVelocity;
  int numSeats;
  int weight;
  int[] wheelPressure;
  void Car() {
    this.name := "name";
    this.maximumVelocity := 120;
    this.numSeats := 6;
    this.weight := 9800;
  }
  void setName(string name) {
    this.name := name;
  }
  int velocityTimesConstant(int c) {
    return this.maximumVelocity * c;
  }
  string getName() {
    return this.name;
  }
  int uselessMethod(int a, int b, int c) {
    return 0;
  }
}

