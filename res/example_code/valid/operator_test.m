class operatorTest {
	void main(string[] args)
	{
		var number := 3;
    if (number != 3) {
      "err";
    }
		number := number - 5;
    if (number != -2) {
      "err";
    }
		var othernumber := - 5;
    if (othernumber != -5) {
      "err";
    }
		number := number - othernumber;
    if (number > 3 || number < 3) {
      "err";
    }
    number.print();
	}
}
