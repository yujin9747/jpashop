package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // setter가 필요없는 entity의 경우, constructor에서 값을 세팅하고, 이후에는 고치지 못하도록 하는 것이 좋은 설계
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // 기본생성자를 사용해주지 않으면 jpa에서 오류를 내기 때문에 public이 아닌 protected로 기본생성자를 만들어 준다.
    protected Address() {}
}
