// Order 클래스: 주문을 나타내는 클래스
class Order {
    private int quantity; // 주문 수량
    private double price; // 단가

    // 생성자: 주문 수량과 단가를 초기화
    public Order(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    // 할인을 적용한 주문 총액을 계산하는 메서드
    double calculateTotal(double discount) {
        // 할인율을 적용한 주문 총액을 계산하여 반환
        return (quantity * price) * (1 - discount);
    }
}

// Main 클래스: 주문과 할인 적용을 테스트하는 메인 클래스
public class Main2 {
    public static void main(String[] args) {
        // 주문 객체 생성
        Order order = new Order(5, 10.0);
        
        // 주문 총액을 할인율 0.2 (20%)로 계산
        double total = order.calculateTotal(0.2); // 할인율을 직접 전달
        System.out.println("Total with Discount: " + total);
    }
}
