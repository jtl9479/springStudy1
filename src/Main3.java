// Order 클래스: 주문 정보를 담고 주문 총액을 계산하는 클래스
class Order {
    private int quantity; // 주문 수량
    private double price; // 단가

    // 생성자: 주문 수량과 단가를 초기화
    public Order(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    // 주문 총액을 계산하는 메서드
    double calculateTotal() {
        return quantity * price; // 수량과 단가를 곱한 값이 주문 총액
    }
}

// Main 클래스: 주문과 주문 총액 계산을 테스트하는 메인 클래스
public class Main3 {
    public static void main(String[] args) {
        // 주문 객체 생성
        Order order = new Order(5, 10.0);
        double total = order.calculateTotal(); // 주문 총액 계산
        System.out.println("Total: " + total); // 주문 총액 출력
    }
}
