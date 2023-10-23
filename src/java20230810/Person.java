package java20230810;

// Shape 클래스 (기반 클래스)
abstract class Shape {
    abstract double calculateArea();
}

// Rectangle 클래스 (Shape을 확장하는 새로운 클래스)
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double calculateArea() {
        return width * height;
    }
}

// Circle 클래스 (Shape을 확장하는 새로운 클래스)
class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// 계산기 클래스
class AreaCalculator {
    double calculateTotalArea(Shape[] shapes) {
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }
}

public class Person {
    public static void main(String[] args) {
        Shape[] shapes = {new Rectangle(5, 10), new Circle(3)};
        
        AreaCalculator calculator = new AreaCalculator();
        double totalArea = calculator.calculateTotalArea(shapes);
        
        System.out.println("Total Area: " + totalArea);
    }
}
