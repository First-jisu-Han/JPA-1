package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable    // 내장타입임을 알림
@Getter
public class Address {

    private String city;
    private String street;
    private String zipCode;

    // jpa 스펙상 임베디드 타입이나 , 엔티티는 자바기본 생성자를 public 이나 protected 로 설정해야한다. (jpa구현라이브러리가 객체 생성할때 기술을 사용하기위함)
    protected Address(){
    }

    public Address(String city,String street,String zipCode){
        this.city=city;
        this.street=street;
        this.zipCode=zipCode;
    }
}
