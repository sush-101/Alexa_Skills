
abstract class Shapes {
 int a=3,b=5;
  void printArea() {
	 
  } 
}
class Rectangle extends Shapes{
	void printArea() {
		System.out.println(a*b);
	}
}
class Triangle extends Shapes{
	void printArea() {
		System.out.println(1/2*a*b);
	}
}
class Circle extends Shapes{
	void printArea() {
		System.out.println(3.14*a*b);
	}
}
class Shape{
	public static void main(String args[]) {
		Shapes d=new Rectangle();
		d.printArea();
	}
	
	
}
