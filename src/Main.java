/*
 * 위의 예제에서 Order 클래스는 calculateTotal 메서드를 통해 주문 총액을 계산합니다. 
 * 이제 새로운 요구사항으로 할인 상품을 추가하려고 한다고 가정해보겠습니다. 
 * 이를 위해 코드를 수정해야 하며, 이는 OCP를 위반하는 상황입니다.
 * 
 */
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

// DiscountedOrder 클래스: 할인 적용된 주문을 나타내는 클래스
class DiscountedOrder extends Order {
    private double discount; // 할인율

    // 생성자: 주문 수량, 단가, 할인율을 초기화
    public DiscountedOrder(int quantity, double price, double discount) {
        super(quantity, price); // 부모 클래스의 생성자 호출
        this.discount = discount; // 할인율 초기화
    }

    // 할인 적용한 주문 총액을 계산하는 메서드 (오버라이드)
    @Override
    double calculateTotal() {
        double originalTotal = super.calculateTotal(); // 부모 클래스의 주문 총액 계산
        return originalTotal * (1 - discount); // 할인율 적용하여 총액 계산
    }
}

// Main 클래스: 주문과 할인 적용을 테스트하는 메인 클래스
public class Main {
    public static void main(String[] args) {
        // 주문 객체 생성
        Order order = new Order(5, 10.0);
        double total = order.calculateTotal(); // 주문 총액 계산
        System.out.println("Total: " + total);

        // 할인 적용된 주문 객체 생성
        DiscountedOrder discountedOrder = new DiscountedOrder(5, 10.0, 0.2);
        double discountedTotal = discountedOrder.calculateTotal(); // 할인 적용한 주문 총액 계산
        System.out.println("Discounted Total: " + discountedTotal);
    }
}
