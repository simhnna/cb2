class A {
  void firstFunction() {

  }

  int returnsConstant() {
    return 1;
  }

  int fibonacci(int n) {

  }

  int returnArgument(int n) {
    return n;
  }

  int additiveInverse(int n) {
    return -n;
  }

  int identity(int n) {
    return - - - - n;
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

  // should return zero
  int messedUpStatement() {
    return 1 - - - - 2 + - 2 - 1;
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
    }
    var result := n;
    result := result * factorial(n - 1);
    return result;
  }

  int nonRecursiveFactorial(int n) {
    var result := 1;
    while (n > 1) {
      result := result * n;
      n := n - 1;
    }
    return result;
  }

  void main(string[] args) {
    print(nonRecursiveFactorial(1));
    print(nonRecursiveFactorial(otherFactorial(2)));
    print(args.length);
    var car := new <Car>;
    print(car.name);
    car.name.print();
    car.setName("whhhhaaat");
    if (car.getName() != "whhhhaaat" || car.name != car.getName()) {
      "Noooo!".print();
    }
    if (car.getName().length != car.name.length) {
      "Noo".print();
    } else {
      "Yeeees".print();
    }
    print(car.velocityTimesConstant(100));
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
